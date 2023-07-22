package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {

    @Query("select s from Student s where s.faculty.id = :facultyId")
    Collection<Student> getStudentsByFacultyId(Long facultyId);
    List<Faculty> findByColorIgnoreCase(String color);
    List<Faculty> findByNameIgnoreCase(String facultyName);


}
