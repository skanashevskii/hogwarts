package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;


import java.util.*;


@Service
public class FacultyService {
    //private final Map<Long, Faculty> facultyHogwarts = new HashMap<>();
    private FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    //private long lastId = 0;

    /* public Faculty createFaculty(Faculty faculty) {
         faculty.setId(++lastId);
         facultyHogwarts.put(lastId, faculty);
         return faculty;
     }*/
    public Faculty createFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public Faculty findFacultyById(long id) {
        return facultyRepository.findById(id).get();
    }
    public List<Faculty> findByName(String facultyName) {
        return facultyRepository.findByName(facultyName);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);

    }

    /* public Collection<Faculty> findByColor(String color) {
         ArrayList<Faculty> result = new ArrayList<>();
         for (Faculty faculty : facultyHogwards.values()) {
             if (Objects.equals(faculty.getColor(), color)) {
                 result.add(faculty);
             }
         }
         return result;
     }*/
    //2 вариант
    public Collection<Faculty> findByColor(String color) {

        return facultyRepository.findByName(color);

    }
}
