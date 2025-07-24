package com.unitap;

import com.google.cloud.spring.data.firestore.repository.config.EnableReactiveFirestoreRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableReactiveFirestoreRepositories(basePackages = "com.unitap.repository")
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
