package ru.hogwarts.school.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.school.controllers.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class FacultyControllerRestTemplateTest {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;
  /*  @BeforeEach
    public void setUp() {
        studentRepository.deleteAll();
    }*/
    @AfterEach
    public void cleanUp() {
        studentRepository.deleteAll();
    }

    @Test
    public void contextLoads() throws Exception{
        Assertions.assertThat(facultyController).isNotNull();
    }
    @Test
    public void findFacultiesAll() throws Exception{
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/faculty/all",String.class))
                .isNotNull();
    }
    @Test
    public void findFacultyById() throws Exception{
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/faculty/1",String.class))
                .isNotNull();
    }
    @Test
    public void testPostFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Any");
        faculty.setColor("blue");

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:"+port+"/faculty",faculty,String.class))
                .isNotNull();
    }
    @Test
    public void testDeleteFaculty() throws Exception{
        ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:"+port+ "/faculty/5",
                HttpMethod.DELETE,null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
    @Test
    public void testFindFacultiesByColor() throws Exception {
        String color = "red"; // Нужный цвет для тестирования

        ResponseEntity<Collection<Faculty>> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty/byColorOrName?color="+color,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
               //String.class
        );
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());

       assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Проверяем не пусто ли тело ответа (при условии,
        // что есть факультеты с указанным цветом)
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).asList().isNotEmpty();
    }
    @Test
    public void testFindFacultiesByName() throws Exception {
        String nameFaculty = "Gryffindor"; // Имя факультета для тестирования

        ResponseEntity<Collection<Faculty>> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty/byColorOrName?nameFaculty=" + nameFaculty,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {} // Correct type parameter
        );
        // Print the response for debugging
        System.out.println("Response: " + response.getBody());

        // Validate the response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Collection<Faculty> faculties = response.getBody();

        // Check if the list of faculties contains faculties with the specified name (case-insensitive)
        assert faculties != null;
        boolean hasFacultyWithName = faculties.stream()
                .anyMatch(faculty -> faculty.getName().equalsIgnoreCase(nameFaculty));
        assertThat(hasFacultyWithName).isTrue();

    }

    @Test
    @Sql(scripts = {"/insert-test-data.sql"}) // Путь к SQL-скрипту с тестовыми данными
    public void testGetStudentsByFacultyId() throws Exception {
        long facultyId = 1L; // ID факультета для теста
        // Фиктивный список студентов для теста
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "Тестовый1", 20));
        students.add(new Student(2L, "Тестовый2", 23));

        // GET запрос к ендпоинту
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + facultyId + "/students",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(students);

    }
    @Test
    public void testEditFaculty() throws Exception {
        long facultyId = 1L;

        // Обновленный объект факультета с изменениями
        Faculty updatedFaculty = new Faculty(facultyId, "Gryffindor", "Red");

        // Настройка заголовка запроса
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

        // Сущность HTTP с обновленным объектом факультета в теле запроса
        HttpEntity<Faculty> requestEntity = new HttpEntity<>(updatedFaculty, headers);

        // PUT запрос к ендпоинту
        ResponseEntity<Faculty> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.PUT,
                requestEntity,
                Faculty.class
        );

        // Проверка
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty editedFaculty = response.getBody();
        assertThat(editedFaculty).isNotNull();
        assert editedFaculty != null;
        assertThat(editedFaculty.getId()).isEqualTo(facultyId);
        assertThat(editedFaculty.getName()).isEqualTo(updatedFaculty.getName());
        assertThat(editedFaculty.getColor()).isEqualTo(updatedFaculty.getColor());
    }






}


