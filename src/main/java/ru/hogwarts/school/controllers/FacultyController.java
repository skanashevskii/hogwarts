package ru.hogwarts.school.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;


import java.util.Collection;
import java.util.Collections;


@RestController
@RequestMapping("faculty")
@Tag(name = "Факультеты", description = "Методы работы с факультетами")

public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    @Operation(summary = "Информация о факультете")
    public ResponseEntity<Faculty> getFacultyInfo(@Parameter(description = "ID факультета")
                                                  @PathVariable long id) {
        Faculty faculty = facultyService.findFacultyById(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/{facultyId}/students")
    @Operation(summary = "")
    public Collection<Student> getStudentsByFacultyId(@PathVariable Long facultyId) {
       return facultyService.getStudentsByFacultyId(facultyId);
    }
/*    @GetMapping("{facultyId}/students")
    @Operation(summary = "Получение студентов факультета по его ID")
    public ResponseEntity<Collection<Student>> getStudentsByFacultyId(@PathVariable Long facultyId) {
        Collection<Student> students = facultyService.getStudentsByFacultyId(facultyId);
        if (!students.isEmpty()) {
            return ResponseEntity.ok(students);
        }
        return ResponseEntity.notFound().build();
    }*/

    @PostMapping
    @Operation(summary = "Создание факультета")
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    @Operation(summary = "Редактирование факультета")
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление факультета")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("byColor")
    @Operation(summary = "Сортировка по цвету или имени")
    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(required = false) String color,
                                                             @RequestParam(required = false) String name) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColorOrName(color,name));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

}
