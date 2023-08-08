package ru.hogwarts.school.controller;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//Тесты через реальный сервер
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerRestTemplateTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired//инжектим для очистки БД после теста
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @BeforeEach
    void setUp() {
        //чистим БД
        studentRepository.deleteAll();
    }

    @Test
    public void findStudents() throws Exception {
        ResponseEntity<List<Student>> result = restTemplate.exchange("http://localhost:"
                        + port + "/student/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){});
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();


    }


    @Test
    public void findStudentById() throws Exception {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Harry");
        studentDTO.setAge(16);
        StudentDTO saved = restTemplate.postForObject("http://localhost:"
                + port + "/student", studentDTO, StudentDTO.class);

        StudentDTO result = this.restTemplate.getForObject("http://localhost:"
                + port + "/student/" + saved.getId(), StudentDTO.class);
        Assertions.assertThat(result.getName()).isEqualTo("Harry");
        Assertions.assertThat(result.getAge()).isEqualTo(16);
    }

    @Test
    void testPostStudent() {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Ron");
        studentDTO.setAge(16);
        StudentDTO result = restTemplate.postForObject("http://localhost:"
                + port + "/student", studentDTO, StudentDTO.class);
        Assertions.assertThat(result.getAge()).isEqualTo(16);
        Assertions.assertThat(result.getName()).isEqualTo("Ron");
        Assertions.assertThat(result.getFacultyId()).isEqualTo(0L);
    }
    @Test
    void testPutStudent() throws Exception{
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Harry");
        studentDTO.setAge(16);
        StudentDTO saved = restTemplate.postForObject("http://localhost:"
                + port + "/student", studentDTO, StudentDTO.class);

        saved.setName("Germiona");

        ResponseEntity<Student> studentEntity = restTemplate.exchange(
                "http://localhost:"+port+"/student",
                HttpMethod.PUT,
                new HttpEntity<>(saved),
                Student.class);
        Assertions.assertThat(studentEntity.getBody().getName()).isEqualTo("Germiona");
        Assertions.assertThat(studentEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }














}
