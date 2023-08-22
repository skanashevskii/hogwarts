package ru.hogwarts.school.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;


import java.util.*;


@RestController
@RequestMapping("faculty")
@Tag(name = "Факультеты", description = "Методы работы с факультетами")

public class FacultyController {
    private final FacultyService facultyService;
    private final FacultyRepository facultyRepository;

    public FacultyController(FacultyService facultyService, FacultyRepository facultyRepository) {
        this.facultyService = facultyService;
        this.facultyRepository = facultyRepository;
    }
    @GetMapping("/longest-name")
    @Operation(summary = "Самое длинное имя")
    public String getLongestFacultyName() {
        return facultyService.getLongestFacultyName();
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
    @Operation(summary = "Инфо о студентах факультета")
    public Collection<Student> getStudentsByFacultyId(@PathVariable long facultyId) {
       return facultyService.getStudentsByFacultyId(facultyId);

    }


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
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление факультета")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/byColorOrName")
    @Operation(summary = "Сортировка по цвету или имени")
    public ResponseEntity<Collection<Faculty>> findFaculties(@RequestParam(required = false) String color,
                                                             @RequestParam(required = false) String nameFaculty) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findAllbyColorIgnoreCase(color));
        }
        if (nameFaculty != null && !nameFaculty.isBlank()) {
            return ResponseEntity.ok(facultyService.findAllbyNameIgnoreCase(nameFaculty));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }


}
