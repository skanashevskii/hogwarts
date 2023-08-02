package ru.hogwarts.school;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controllers.StudentController;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplate {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception{
        Assertions.assertThat(studentController).isNotNull();
    }
    @Test
    public void findStudents() throws Exception{
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student/all",String.class))
                .isNotNull();
    }
    @Test
    public void findStudentById() throws Exception{
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student/1",String.class))
                .isNotNull();
    }
    @Test
    public void findStudentsByIdFaculty() throws Exception{
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student/2/faculty",Object.class))
                .isNotNull();
    }

}
