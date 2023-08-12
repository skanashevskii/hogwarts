package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByNameContainingIgnoreCase(String name);

    Collection<Student> findByNameIgnoreCase(String name);

    Collection<Student> findByAgeBetween(int minAge, int maxAge);

    Collection<Student> findByAge(int age);

    Collection<Student> findAllByFaculty_Id(long facultyId);

    @Query(value = "select count(*) from student", nativeQuery = true)
    Integer getCountStudents();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    Integer getAvgAgeStudents();
    @Query(value = "select * from student order by \"id\" desc limit 5", nativeQuery = true)
    Collection<Student> getLastFiveStudents ();


}
