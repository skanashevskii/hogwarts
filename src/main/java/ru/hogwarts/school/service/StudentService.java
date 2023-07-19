package ru.hogwarts.school.service;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;


@Service
public class StudentService {
    //private final Map<Long, Student> studentHogwarts = new HashMap<>();
    //private long lastId = 0;
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /*   public Student createStudent(Student student) {
           student.setId(++lastId);
           studentHogwarts.put(lastId, student);
           return student;
       }*/
    public Student createStudent(Student student){
        return studentRepository.save(student);
    }


    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }
    public List<Student> findByName(String studentName) {
        return studentRepository.findByName(studentName);
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
       studentRepository.deleteById(id);
    }

    @Operation(summary = "Сортировка по возрасту")
    public Collection<Student> findByAge(int age) {
      return studentRepository.findByAge(age);
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }
}
