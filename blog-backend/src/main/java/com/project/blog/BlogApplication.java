package com.project.blog;

import com.project.blog.entity.User;
import com.project.blog.repository.UserRepository;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.project.blog.*")
public class BlogApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    public BlogApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${application-description}")
                                 String description, @Value("${application-version")
                                 String version) {
        return new OpenAPI()
                .info(new Info().title("Blog Application API")
                        .version(version)
                        .description(description)
                        .license(new License().name("Blog Application API Licence")));
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");

        userRepository.save(user);
    }
}
