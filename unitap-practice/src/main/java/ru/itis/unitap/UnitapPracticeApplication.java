package ru.itis.unitap;

import com.google.cloud.spring.data.firestore.repository.config.EnableReactiveFirestoreRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableReactiveFirestoreRepositories(basePackages = "ru.itis.unitap.repository")
@SpringBootApplication
public class UnitapPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnitapPracticeApplication.class, args);
    }

}
