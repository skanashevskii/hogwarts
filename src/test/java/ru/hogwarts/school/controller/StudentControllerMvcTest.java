package ru.hogwarts.school.controller;


import org.json.JSONObject;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import ru.hogwarts.school.controllers.AvatarController;
import ru.hogwarts.school.controllers.FacultyController;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerMvcTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private FacultyService facultyService;
    @SpyBean
    private AvatarService avatarService;
    @InjectMocks
    private StudentController studentController;
    @InjectMocks
    private FacultyController facultyController;
    @InjectMocks
    private AvatarController avatarController;

    @Test
    public void crudStudentTest() throws Exception {

        final long id = 1;
        final String name = "ivan ivanov";
        final int age = 25;
        LOGGER.info("Received request to save student: name={},age={}", name, age);
        // Создание объекта JSON, представляющего учащегося
        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        // Mock (имитация) поведения методов save и findById репозитория
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        // Запрос POST, чтобы сохранить учащегося
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        // Запрос GET, чтобы получить учащегося по идентификатору.
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
        // Запрос PUT, сохранение изменений.
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
        // Запрос DELETE, удаление студента.
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id)
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Дополнительные выходные данные отладки для проверки ответа JSON
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        LOGGER.info("Response JSON: {}", result.getResponse().getContentAsString());

    }

    @Test
    public void testGetFacultyByStudentId() throws Exception {
        // Подготовка объекта учащегося к тестированию
        long studentId = 1;
        String studentName = "John Doe";
        int studentAge = 20;
        Student student = new Student(studentId, studentName, studentAge);

        // Образец объекта факультета для тестирования
        long facultyId = 101;
        String facultyName = "Computer Science";
        String facultyColor = "Blue";
        Faculty faculty = new Faculty(facultyId, facultyName, facultyColor);

        // Установка факультет для студента
        student.setFaculty(faculty);

        // Mock the service behavior
        when(studentService.findStudentById(studentId)).thenReturn(student);

        // Запрос GET к /student/{studentId}/faculty
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/{studentId}/faculty", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor));
    }

    @Test
    public void testGetFacultyByStudentIdNotFound() throws Exception {
        // Подготовка образца объекта учащегося к тестированию
        long studentId = 1;
        String studentName = "John Doe";
        int studentAge = 20;
        Student student = new Student(studentId, studentName, studentAge);

        //  Поведением службы, чтобы вернуть студента без факультета
        when(studentService.findStudentById(studentId)).thenReturn(student);

        // Запрос GET к /student/{studentId}/faculty
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/{studentId}/faculty", studentId))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testFindStudentsByAgeBetween() throws Exception {
        //  Список студентов для тестирования
        List<Student> students = Arrays.asList(
                new Student(1L, "John Doe", 20),
                new Student(2L, "Jane Smith", 22),
                new Student(3L, "Michael Johnson", 25)
        );

        // Mock the service behavior to return the list of students
        when(studentService.findByAgeBetween(20, 25)).thenReturn(students);

        // Запрос GET к /student/byAgeBetween с параметрами minAge=20 и maxAge=25
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/byAgeBetween")
                        .param("minAge", "20")
                        .param("maxAge", "25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"))
                .andExpect(jsonPath("$[1].age").value(22))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("Michael Johnson"))
                .andExpect(jsonPath("$[2].age").value(25));
    }

    @Test
    public void testFindStudentsByAgeBetweenEmptyList() throws Exception {
        // Имитация поведения службы, чтобы вернуть пустой список учащихся
        when(studentService.findByAgeBetween(20, 25)).thenReturn(Collections.emptyList());

        // Запрос GET к /student/byAgeBetween с параметрами minAge=20 и maxAge=25
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/byAgeBetween")
                        .param("minAge", "20")
                        .param("maxAge", "25"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }


}


