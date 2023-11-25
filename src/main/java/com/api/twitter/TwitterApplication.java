package com.api.twitter;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;

@SpringBootApplication
public class TwitterApplication {

	public static void main(String[] args) throws Exception {
		initializeFirebaseApp();
		SpringApplication.run(TwitterApplication.class, args);
	}

	private static void initializeFirebaseApp() throws Exception {
		String firebaseAccountKeyPath = "./src/main/resources/firebase-service-account-key.json";
		FileInputStream serviceAccountFile = null;

		try {
			serviceAccountFile = new FileInputStream(firebaseAccountKeyPath);
			FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccountFile))
					.build();
			FirebaseApp.initializeApp(firebaseOptions);
			serviceAccountFile.close();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
