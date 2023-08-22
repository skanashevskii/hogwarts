package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;



import java.util.*;


@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;

    }
    public String getLongestFacultyName() {
        List<Faculty> faculties = facultyRepository.findAll();

        Optional<String> longestFacultyName = faculties.stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length));

        return longestFacultyName.orElse("No faculty found");
    }

    public Faculty createFaculty(Faculty faculty) {
        LOGGER.info("Creating faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty findFacultyById(long id) {
        LOGGER.info("Finding faculty: {}", id);
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        LOGGER.info("Editing faculty: {}", faculty);
        return facultyRepository.findById(faculty.getId())
                .map(dbEntity ->{
                    dbEntity.setName(faculty.getName());
                    dbEntity.setColor(faculty.getColor());
                    facultyRepository.save(dbEntity);
                    return dbEntity;
                })
                .orElse(null);
    }

    public boolean deleteFaculty(long id) {
        LOGGER.info("Deleting faculty: {}", id);
        return facultyRepository.findById(id)
                .map(entity -> {
                    facultyRepository.delete(entity);
                    return true;
                })
                .orElse(false);

    }

    public Collection<Faculty> findAllbyColorIgnoreCase(String color) {
        LOGGER.info("Finding all by color (case insensitive): {}", color);
        return facultyRepository.findByColorIgnoreCase(color);

    }
    public Collection<Faculty> findAllbyNameIgnoreCase(String nameFaculty) {
        LOGGER.info("Finding all by name (case insensitive): {}", nameFaculty);
        return facultyRepository.findByNameIgnoreCase(nameFaculty);

    }

    public Collection<Student> getStudentsByFacultyId(long facultyId) {
        LOGGER.info("Finding students by faculty ID: {}", facultyId);
        Faculty faculty = facultyRepository.findById(facultyId).orElse(null);
        if (faculty != null) {
            Collection<Student> students = faculty.getStudents();
            LOGGER.info("Found {} students for faculty ID: {}", students.size(), facultyId);
            return students;
        } else {
            LOGGER.warn("Faculty not found for ID: {}", facultyId);
            return Collections.emptyList();
        }
    }


}
