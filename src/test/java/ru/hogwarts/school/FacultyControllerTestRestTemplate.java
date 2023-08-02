package ru.hogwarts.school;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controllers.FacultyController;
import ru.hogwarts.school.controllers.StudentController;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTestRestTemplate {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

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

}
