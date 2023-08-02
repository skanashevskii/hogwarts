package ru.hogwarts.school.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.dto.StudentMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;


@RestController
@RequestMapping("student")
@Tag(name = "Студенты", description = "Методы работы со студентами")

public class StudentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("all")
    @Operation(summary = "Информация о всех студентах,по имени,по части имени")
    public ResponseEntity findStudents(@RequestParam(required = false) String name){

        if(name != null && !name.isBlank()){
            return ResponseEntity.ok(studentService.findByNameContainingIgnoreCase(name));
        }
        return ResponseEntity.ok(studentService.getAllStudent());
    }
    @GetMapping("all/2")
    @Operation(summary = "Информация о всех студентах, по имени, по части имени")
    public ResponseEntity<Collection<StudentDTO>> findStudents2(@RequestParam(required = false) String name) {
        Collection<StudentDTO> students;
        if (name != null && !name.isBlank()) {
            students = studentService.findByNameIgnoreCase(name);
        } else {
            students = studentService.getAllStudents2();
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "Информация о студенте по id")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
    @GetMapping("{id}/2")
    @Operation(summary = "Информация о студенте по id")
    public ResponseEntity<StudentDTO> getStudentInfo2(@PathVariable Long id) {
        StudentDTO studentDTO = studentService.findStudentById2(id);
        if (studentDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }
    @GetMapping("{studentId}/faculty")
    @Operation(summary = "Получение студентов факультета по его ID")
    public ResponseEntity<Faculty> getFacultyByStudentId(@PathVariable Long studentId) {
        Student student = studentService.findStudentById(studentId);
        if (student == null || student.getFaculty() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student.getFaculty());
    }


    @PostMapping
    @Operation(summary = "Создание студента")
    public Student createStudent(@RequestBody Student student) {
        LOGGER.info("Received request to save student: {}", student);
        return studentService.createStudent(student);
    }
    @PostMapping("/create2")
    @Operation(summary = "Создание студента2")
    public ResponseEntity<StudentDTO> createStudent2(@RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Изменение инфо о студенте")
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }
    @PutMapping("/2")
    @Operation(summary = "Изменение инфо о студенте")
    public ResponseEntity<StudentDTO> editStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO editedStudent = studentService.editStudent2(studentDTO);
        if (editedStudent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(editedStudent, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление студента")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("age")
    @Operation(summary = "Поиск студентов(а) по возрасту")
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
    @GetMapping("age/2")
    @Operation(summary = "Поиск студентов(а) по возрасту")
    public ResponseEntity<Collection<StudentDTO>> findStudents2(@RequestParam int age) {
        Collection<StudentDTO> students;
        if (age > 0) {
            students = studentService.findByAge2(age);
        } else {
            students = Collections.emptyList();
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/byAgeBetween")
    @Operation(summary = "Поиск студентов по возрасту в диапазоне между min и max")
    public ResponseEntity<Collection<Student>> findStudentsByAgeBetween(@RequestParam int minAge,
                                                                        @RequestParam int maxAge) {
        if (minAge > 0) {
            return ResponseEntity.ok(studentService.findByAgeBetween(minAge,maxAge));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
    @GetMapping("/byAgeBetween/2")
    @Operation(summary = "Поиск студентов по возрасту в диапазоне между min и max")
    public ResponseEntity<Collection<StudentDTO>> findStudentsByAgeBetween2(@RequestParam int minAge,
                                                                     @RequestParam int maxAge) {
        Collection<StudentDTO> students;
        if (minAge > 0) {
            students = studentService.findByAgeBetween2(minAge, maxAge);
        } else {
            students = Collections.emptyList();
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @GetMapping("/byFaculty")
    @Operation(summary = "Поиск студентов по Id факультета")
    public Collection<Student> findStudentByFaculty(@RequestParam long faculId){
        return studentService.findStudentByFaculty(faculId);
    }
    @GetMapping("/byFaculty/2")
    @Operation(summary = "Поиск студентов по Id факультета")
    public ResponseEntity<Collection<StudentDTO>> findStudentByFaculty2(@RequestParam long faculId) {
        Collection<StudentDTO> students = studentService.findStudentByFaculty2(faculId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
