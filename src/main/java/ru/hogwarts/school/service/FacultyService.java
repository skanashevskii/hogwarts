package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;



import java.util.*;


@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;

    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFacultyById(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
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
        return facultyRepository.findById(id)
                .map(entity -> {
                    facultyRepository.delete(entity);
                    return true;
                })
                .orElse(false);

    }

    public Collection<Faculty> findAllbyColorIgnoreCase(String color) {

        return facultyRepository.findByColorIgnoreCase(color);

    }
    public Collection<Faculty> findAllbyNameIgnoreCase(String nameFaculty) {

        return facultyRepository.findByNameIgnoreCase(nameFaculty);

    }

    public Collection<Student> getStudentsByFacultyId(Long facultyId) {
        // Находим факультет по идентификатору
        Collection<Student> faculty = facultyRepository.getStudentsByFacultyId(facultyId);
        if (faculty != null) {
            // Получаем список студентов, принадлежащих данному факультету
            return Collections.unmodifiableCollection(faculty);
        }
        return Collections.emptyList(); // Возвращаем пустой список, если факультет с таким идентификатором не найден
    }
}
