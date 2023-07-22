package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudent() {

        Student student = new Student();
        when(studentRepository.save(any(Student.class))).thenReturn(student);


        Student result = studentService.createStudent(student);


        assertEquals(student, result);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testFindStudent() {

        long studentId = 1;
        Student student = new Student();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));


        Student result = studentService.findStudentById(studentId);


        assertEquals(student, result);
        verify(studentRepository, times(1)).findById(studentId);
    }

  /*  @Test
    void testFindByName() {

        String studentName = "John Doe";
        List<Student> students = new ArrayList<>();
        //when(studentRepository.findByName(studentName)).thenReturn(students));


        Student result = studentService.findByName(studentName);


        assertEquals(students, result);
        verify(studentRepository, times(1)).findByName(studentName);
    }*/

    @Test
    void testEditStudent() {

        Student student = new Student();
        when(studentRepository.save(any(Student.class))).thenReturn(student);


        Student result = studentService.editStudent(student);


        assertEquals(student, result);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testDeleteStudent() {

        long studentId = 1;


        studentService.deleteStudent(studentId);


        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void testFindByAge() {

        int age = 20;
        List<Student> students = new ArrayList<>();
        when(studentRepository.findByAge(age)).thenReturn(students);


        Collection<Student> result = studentService.findByAge(age);


        assertEquals(students, result);
        verify(studentRepository, times(1)).findByAge(age);
    }

    @Test
    void testGetAllStudent() {

        List<Student> students = new ArrayList<>();
        when(studentRepository.findAll()).thenReturn(students);


        Collection<Student> result = studentService.getAllStudent();


        assertEquals(students, result);
        verify(studentRepository, times(1)).findAll();
    }
}

