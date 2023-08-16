package ru.hogwarts.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HogwartsApplication {

    public static void main(String[] args) {
        // Запуск приложения с профилем "dev"
        System.setProperty("spring.profiles.active", "dev");
        // Запуск приложения с профилем "prod"
        //System.setProperty("spring.profiles.active", "prod");
        SpringApplication.run(HogwartsApplication.class, args);
    }

}
