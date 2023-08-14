package ru.hogwarts.school.controller;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.hogwarts.school.controllers.AvatarController;


import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.io.IOException;
import java.nio.file.Files;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AvatarControllerRestTemplateTest {

    @LocalServerPort
    private int port;
    @Autowired
    private AvatarController avatarController;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AvatarRepository avatarRepository;

    private String baseUrl;



    @BeforeEach
    @Transactional
    public void setUp() {

        baseUrl = "http://localhost:" + port;
        // Добавьте тестовые данные в базу данных
        Student student = new Student();
        student.setId(1L);
        student.setName("Тестовый");
        student.setAge(20);
        studentRepository.save(student);
    }
    @AfterEach
    public void cleanUp(){
        //чистим БД
        studentRepository.deleteAll();
        avatarRepository.deleteAll();
    }
    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(avatarController).isNotNull();
    }

    @Test
    public void testUploadAvatar() throws IOException {
        Long studentId = 1L;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create a temporary file with content for the avatar
        byte[] avatarContent = Files.readAllBytes(new ClassPathResource("test-avatar.png").getFile().toPath());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("avatar", new org.springframework.core.io.ByteArrayResource(avatarContent) {
            @Override
            public String getFilename() {
                return "test-avatar.png";
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/avatar/" + studentId + "/avatar", HttpMethod.POST, requestEntity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

