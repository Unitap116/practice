package ru.unitap.profile;

import me.paulschwarz.springdotenv.DotenvPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class ProfileApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(ProfileApplication.class);
    ConfigurableApplicationContext context = app.run(args);

    ConfigurableEnvironment env = context.getEnvironment();

    DotenvPropertySource.addToEnvironment(env);
  }
}
