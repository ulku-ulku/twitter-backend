package com.api.twitter.implementation;

import com.api.twitter.entity.User;
import com.api.twitter.model.request.SignUpRequest;
import com.api.twitter.model.response.UserDetailResponse;
import com.api.twitter.repository.FollowerRepository;
import com.api.twitter.repository.UserRepository;
import com.api.twitter.service.UserService;
import com.google.firebase.auth.FirebaseToken;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @Override
    public void register(SignUpRequest signUpRequest) throws Exception {
        try {
            UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                    .setEmail(signUpRequest.getEmail())
                    .setPassword(signUpRequest.getPassword());
            UserRecord firebaseResponse = FirebaseAuth
                    .getInstance()
                    .createUser(createRequest);
            if (firebaseResponse == null && StringUtils.isBlank(firebaseResponse.getUid())) {
                throw new Exception("User registration failed due to invalid response");
            }

            User user = User.builder()
                    .username(signUpRequest.getUsername())
                    .email(signUpRequest.getEmail())
                    .registeredDate(new Date())
                    .firebaseUid(firebaseResponse.getUid())
                    .build();

            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public User getUserByTokenId(String userToken) throws Exception {
        if (StringUtils.isNotBlank(userToken) && userToken.startsWith("Bearer ")) {
            userToken = userToken.substring(7);
        }
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(userToken);
            User user = userRepository.findByEmail(decodedToken.getEmail());
            if (user == null) {
                throw new Exception("User not found");
            }

            return user;
        } catch (Exception e) {
            throw new Exception("Invalid token : " + e.getMessage());
        }
    }

    @Override
    public UserDetailResponse convertToUserResponse(User user) throws Exception {
        byte[] bannerImageByte = null;
        if (StringUtils.isNotBlank(user.getBannerPicturePath())) {
            bannerImageByte = Files.readAllBytes(Paths.get(user.getBannerPicturePath()));
        }

        byte[] profileImageByte = null;
        if (StringUtils.isNotBlank(user.getProfilePicturePath())) {
            profileImageByte = Files.readAllBytes(Paths.get(user.getProfilePicturePath()));
        }

        long followers = followerRepository.countByFollowerUserId(user.getId());
        long followees = followerRepository.countByFolloweeUserId(user.getId());

        return UserDetailResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .bio(user.getBio())
                .profileImage(profileImageByte)
                .bannerImage(bannerImageByte)
                .registeredDate(user.getRegisteredDate())
                .followers(followers)
                .followees(followees)
                .build();
    }

    @Override
    public UserDetailResponse getUserById(String id, User loggedInUser) throws Exception {
        User user = userRepository.getById(id);
        boolean isFollowing = followerRepository.existsByFollowerUserIdAndFolloweeUserId(id, loggedInUser.getId());

        UserDetailResponse response = convertToUserResponse(user);
        response.setFollowing(isFollowing);
        return response;
    }

    @Override
    public void updateProfile(User currentUser, MultipartFile profilePicture, MultipartFile bannerPicture, String bio) throws Exception {
        String uploadDirectory = "/Users/dwinanto/Youtube/Twitter/twitter-backend/profile-picture";
        File uploadDir = new File(uploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (bannerPicture != null) {
            String bannerPictureFilename = generateRandomFilename(bannerPicture);
            String bannerPicturePath = uploadDirectory + File.separator + bannerPictureFilename;
            bannerPicture.transferTo(new File(bannerPicturePath));
            currentUser.setBannerPicturePath(bannerPicturePath);
        }

        if (profilePicture != null) {
            String profilePictureFilename = generateRandomFilename(profilePicture);
            String profilePicturePath = uploadDirectory + File.separator + profilePictureFilename;
            profilePicture.transferTo(new File(profilePicturePath));
            currentUser.setProfilePicturePath(profilePicturePath);
        }

        if (StringUtils.isNotBlank(bio)) {
            currentUser.setBio(bio);
        }

        userRepository.save(currentUser);
    }

    private String generateRandomFilename(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String randomName = UUID.randomUUID().toString();
        return randomName + extension;
    }
}
