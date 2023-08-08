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
import ru.hogwarts.school.controllers.AvatarController;
import ru.hogwarts.school.controllers.FacultyController;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.model.Faculty;

import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerMvcTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyController.class);
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
    public void crudFacultyTest() throws Exception {

        final long id = 1;
        final String name = "Slytherin";
        final String color = "red";
        LOGGER.info("Received request to save faculty: name={},color={}", name, color);
        // Создание объекта JSON, представляющего учащегося
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        // Mock (имитация) поведения методов save и findById репозитория
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        doNothing().when(facultyRepository).delete(any(Faculty.class));

        // Запрос POST, чтобы сохранить факультет
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        // Запрос GET, чтобы получить факультет по идентификатору.
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
        // Запрос PUT, сохранение изменений.
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
        // Запрос DELETE, удаление факультета.
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Дополнительные выходные данные отладки для проверки ответа JSON
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        LOGGER.info("Response JSON: {}", result.getResponse().getContentAsString());

    }




    // Тестовый пример для метода getFacultyInfo
    @Test
    public void testGetFacultyInfo() throws Exception {
        // Создание объекта факультета для тестирования
        long facultyId = 1;
        String facultyName = "Test Faculty";
        String facultyColor = "Blue";
        Faculty faculty = new Faculty(facultyId, facultyName, facultyColor);

        // Имитация поведения
        when(facultyService.findFacultyById(facultyId)).thenReturn(faculty);

        // Запрос GET
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/" + facultyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor));
    }

    // Тестовый пример для метода findFaculties
    @Test
    public void testFindFaculties() throws Exception {
        // Create a list of Faculty objects for testing
        List<Faculty> faculties = Arrays.asList(
                new Faculty(1L, "Faculty 1", "Red"),
                new Faculty(2L, "Faculty 2", "Blue"),
                new Faculty(3L, "Faculty 3", "Green")
        );

        // Имитация поведения  для поиска по цвету и имени
        when(facultyService.findAllbyColorIgnoreCase("Red")).thenReturn(faculties.subList(0, 1));
        when(facultyService.findAllbyNameIgnoreCase("Faculty 2")).thenReturn(faculties.subList(1, 2));

        // Запрос GET для поиска по цвету
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/byColorOrName")
                        .param("color", "Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(faculties.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(faculties.get(0).getName()))
                .andExpect(jsonPath("$[0].color").value(faculties.get(0).getColor()));

        //  GET-запрос для поиска имени
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/byColorOrName")
                        .param("nameFaculty", "Faculty 2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(faculties.get(1).getId()))
                .andExpect(jsonPath("$[0].name").value(faculties.get(1).getName()))
                .andExpect(jsonPath("$[0].color").value(faculties.get(1).getColor()));
    }


}


