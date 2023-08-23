package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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


    @Test
    void testEditStudent() {
        // Arrange
        long studentId = 1;
        String newName = "John Doe";
        int newAge = 25;

        Student existingStudent = new Student(studentId, "Jane Smith", 22);

        Student updatedStudent = new Student(studentId, newName, newAge);

        when(studentRepository.findById(studentId)).thenReturn(java.util.Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        // Act
        Student result = studentService.editStudent(updatedStudent);

        // Assert
        assertNotNull(result);
        assertEquals(newName, result.getName());
        assertEquals(newAge, result.getAge());

        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testDeleteStudent() {
        // Arrange
        long studentId = 1;
        Student existingStudent = new Student(studentId, "John Doe", 25);

        when(studentRepository.findById(studentId)).thenReturn(java.util.Optional.of(existingStudent));

        // Act
        studentService.deleteStudent(studentId);

        // Assert
        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).delete(existingStudent);
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

