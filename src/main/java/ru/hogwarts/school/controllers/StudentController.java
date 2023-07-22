package ru.hogwarts.school.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("student")
@Tag(name = "Студенты", description = "Методы работы со студентами")

public class StudentController {
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

    @GetMapping("{id}")
    @Operation(summary = "Информация о студенте по id")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
    @GetMapping("{studentId}/faculty")
    @Operation(summary = "Получение факультета студента по его ID")
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
        return studentService.createStudent(student);
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

    @GetMapping("/byAgeBetween")
    @Operation(summary = "Поиск студентов по возрасту в диапазоне между min и max")
    public ResponseEntity<Collection<Student>> findStudentsByAgeBetween(@RequestParam int minAge,
                                                                        @RequestParam int maxAge) {
        if (minAge > 0) {
            return ResponseEntity.ok(studentService.findByAgeBetween(minAge,maxAge));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
    @GetMapping("/byFaculty")
    public Collection<Student> findStudentByFaculty(@RequestParam long faculId){
        return studentService.findStudentByFaculty(faculId);
    }
}
