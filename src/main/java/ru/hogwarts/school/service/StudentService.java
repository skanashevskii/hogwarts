package ru.hogwarts.school.service;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;


@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudentById(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Collection<Student> findByNameContainingIgnoreCase(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    public Student editStudent(Student student) {
        return studentRepository.findById(student.getId())
                .map(dbEntity -> {
                    dbEntity.setName(student.getName());
                    dbEntity.setAge(student.getAge());
                    studentRepository.save(dbEntity);
                    return dbEntity;
                })
                .orElse(null);
    }

    public void deleteStudent(long id) {
        studentRepository.findById(id)
                .map(entity -> {
                    studentRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Operation(summary = "Сортировка по возрасту")
    public Collection<Student> findByAge(int age) {
        return studentRepository.findByAge(age);
    }

    @Operation(summary = "Сортировка по интервалу возраста")
    public Collection<Student> findByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Collection<Student> findStudentByFaculty(long faculId){
        return studentRepository.findAllByFaculty_Id(faculId);
    }
}
