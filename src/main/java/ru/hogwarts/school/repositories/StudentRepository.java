package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Collection<Student> findByNameContainingIgnoreCase(String name);
    Collection<Student> findByAgeBetween(int minAge,int maxAge);
    Collection<Student> findByAge(int age);
    Collection<Student> findAllByFaculty_Id(long facultyId);
}
