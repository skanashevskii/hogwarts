package ru.hogwarts.school.service;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.dto.StudentMapper;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = StudentMapper.INSTANCE;
    }
    public double getAverageStudentAge() {
        long startTime = System.currentTimeMillis(); // Засекаем время перед выполнением
        List<Student> students = studentRepository.findAll();

        double averageAge = students.stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0); // По умолчанию 0, если нет студентов

        long endTime = System.currentTimeMillis(); // Засекаем время после выполнения
        long executionTime = endTime - startTime; // Вычисляем время выполнения в миллисекундах

        System.out.println("Время выполнения: " + executionTime + " ms");

        return averageAge;
    }
    public List<String> getStudentNamesStartingWithA() {
        long startTime = System.currentTimeMillis(); // Засекаем время перед выполнением
        List<String> studentNamesStartingWithA = studentRepository.findAll()
        .stream()
                .filter(student -> student.getName().startsWith("A"))
                .map(student -> student.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
        long endTime = System.currentTimeMillis(); // Засекаем время после выполнения
        long executionTime = endTime - startTime; // Вычисляем время выполнения в миллисекундах

        System.out.println("Время выполнения: " + executionTime + " ms service");

        return studentNamesStartingWithA;
    }
    public int calculateSum() {
        long startTime = System.currentTimeMillis(); // Засекаем время перед выполнением
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()  // Включение параллельных вычислений
                .reduce(0, (a, b) -> a + b);

        long endTime = System.currentTimeMillis(); // Засекаем время после выполнения
        long executionTime = endTime - startTime; // Вычисляем время выполнения в миллисекундах

        System.out.println("Время выполнения: " + executionTime + " ms");

        return sum;
    }

    public Student createStudent(Student student) {
        LOGGER.info("Was invoked method for create student: {}", student);
        return studentRepository.save(student);
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        LOGGER.info("Was invoked method for create student: {}", studentDTO);
        Student studentEntity = studentMapper.studentDTOToStudentEntity(studentDTO);
        Student savedEntity = studentRepository.save(studentEntity);
        return studentMapper.studentEntityToStudentDTO(savedEntity);
    }

    public Student findStudentById(long id) {
        LOGGER.debug("Was invoked method for seach student by id: {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public StudentDTO findStudentById2(long id) {
        LOGGER.info("Was invoked method for seach student by id: {}", id);
        return studentRepository.findById(id)
                .map(studentMapper::studentEntityToStudentDTO)
                .orElse(null);
    }

    public Integer getCountStudents() {
        LOGGER.info("Trying to get count of students...");
        Integer count = studentRepository.getCountStudents();
        LOGGER.info("Got count of students: {}", count);
        return count;
    }

    public Integer getAvgAgeStudents() {
        LOGGER.debug("Trying to get average age of students...");
        Integer avgAge = studentRepository.getAvgAgeStudents();
        LOGGER.debug("Got average age of students: {}", avgAge);
        return avgAge;
    }

    public Collection<Student> getLastFiveStudents() {
        LOGGER.debug("Trying to get last five students...");
        Collection<Student> students = studentRepository.getLastFiveStudents();
        LOGGER.debug("Got last five students: {}", students);
        return students;
    }

    public Collection<Student> findByNameContainingIgnoreCase(String name) {
        LOGGER.info("Was invoked method for seach student by name: {}", name);
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    public Collection<StudentDTO> findByNameIgnoreCase(String name) {
        LOGGER.info("Was invoked method for seach student by name: {}", name);
        return studentRepository.findByNameContainingIgnoreCase(name).stream()
                .map(studentMapper::studentEntityToStudentDTO)
                .collect(Collectors.toList());
    }

    public Student editStudent(Student student) {
        LOGGER.info("Was invoked method for edit student : {}", student);
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
        LOGGER.info("Was invoked method for edit student : {}", studentDTO);
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
        LOGGER.info("Was invoked method for delete student by id: {}", id);
        studentRepository.findById(id)
                .map(entity -> {
                    studentRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteStudent2(long id) {
        LOGGER.info("Was invoked method for delete student by id: {}", id);
        return studentRepository.findById(id)
                .map(entity -> {
                    studentRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Operation(summary = "Сортировка по возрасту")
    public Collection<Student> findByAge(int age) {
        LOGGER.info("Was invoked method for seach student by age: {}", age);
        return studentRepository.findByAge(age);
    }

    public Collection<StudentDTO> findByAge2(int age) {
        LOGGER.info("Was invoked method for seach student by age: {}", age);
        return studentRepository.findByAge(age).stream()
                .map(studentMapper::studentEntityToStudentDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Сортировка по интервалу возраста")
    public Collection<Student> findByAgeBetween(int minAge, int maxAge) {
        LOGGER.info("Was invoked method for seach student by age interval: {} - {}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Collection<StudentDTO> findByAgeBetween2(int minAge, int maxAge) {
        LOGGER.info("Was invoked method for seach student by age interval: {} - {}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge).stream()
                .map(studentMapper::studentEntityToStudentDTO)
                .collect(Collectors.toList());
    }

    public Collection<Student> getAllStudent() {
        LOGGER.info("Getting all students ...");
        return studentRepository.findAll();
    }

    public Collection<StudentDTO> getAllStudents2() {
        LOGGER.info("Getting all students...");
        return studentRepository.findAll().stream()
                .map(studentMapper::studentEntityToStudentDTO)
                .collect(Collectors.toList());
    }

    public Collection<Student> findStudentByFaculty(long faculId) {
        LOGGER.info("Was invoked method for seach student by facultyId: {}", faculId);
        return studentRepository.findAllByFaculty_Id(faculId);
    }

    public Collection<StudentDTO> findStudentByFaculty2(long facultyId) {
        LOGGER.info("Was invoked method for seach student by facultyId: {}", facultyId);
        return studentRepository.findAllByFaculty_Id(facultyId).stream()
                .map(studentMapper::studentEntityToStudentDTO)
                .collect(Collectors.toList());
    }
}
