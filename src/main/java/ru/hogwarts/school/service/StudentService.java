package ru.hogwarts.school.service;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.dto.StudentMapper;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    private final StudentRepository studentRepository;

    private StudentMapper studentMapper;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = StudentMapper.INSTANCE;
    }

    public Student createStudent(Student student) {
        LOGGER.info("Editing student: {}", student);
        return studentRepository.save(student);
    }
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student studentEntity = studentMapper.studentDTOToStudentEntity(studentDTO);
        Student savedEntity = studentRepository.save(studentEntity);
        return studentMapper.studentEntityToStudentDTO(savedEntity);
    }

    public Student findStudentById(long id) {
        return studentRepository.findById(id).orElse(null);
    }
    public StudentDTO findStudentById2(long id) {
        LOGGER.info("Searching for student by ID: {}", id);
        return studentRepository.findById(id)
                .map(studentMapper::studentEntityToStudentDTO)
                .orElse(null);
    }
    public Integer getCountStudents(){
        return studentRepository.getCountStudents();
    }
    public Integer getAvgAgeStudents(){
        return studentRepository.getAvgAgeStudents();
    }
    public Collection<Student> getLastFiveStudents(){
        return studentRepository.getLastFiveStudents();
    }

    public Collection<Student> findByNameContainingIgnoreCase(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }
    public Collection<StudentDTO> findByNameIgnoreCase(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name).stream()
                .map(studentMapper::studentEntityToStudentDTO)
                .collect(Collectors.toList());
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
    public StudentDTO editStudent2(StudentDTO studentDTO) {
        return studentRepository.findById(studentDTO.getId())
                .map(dbEntity -> {
                    dbEntity.setName(studentDTO.getName());
                    dbEntity.setAge(studentDTO.getAge());
                    Student updatedEntity = studentRepository.save(dbEntity);
                    return studentMapper.studentEntityToStudentDTO(updatedEntity);
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
    public boolean deleteStudent2(long id) {
        return studentRepository.findById(id)
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
    public Collection<StudentDTO> findByAge2(int age) {
        return studentRepository.findByAge(age).stream()
                .map(studentMapper::studentEntityToStudentDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Сортировка по интервалу возраста")
    public Collection<Student> findByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }
    public Collection<StudentDTO> findByAgeBetween2(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge).stream()
                .map(studentMapper::studentEntityToStudentDTO)
                .collect(Collectors.toList());
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Collection<StudentDTO> getAllStudents2() {
        return studentRepository.findAll().stream()
                .map(studentMapper::studentEntityToStudentDTO)
                .collect(Collectors.toList());
    }

    public Collection<Student> findStudentByFaculty(long faculId){
        return studentRepository.findAllByFaculty_Id(faculId);
    }
    public Collection<StudentDTO> findStudentByFaculty2(long facultyId) {
        return studentRepository.findAllByFaculty_Id(facultyId).stream()
                .map(studentMapper::studentEntityToStudentDTO)
                .collect(Collectors.toList());
    }
}
