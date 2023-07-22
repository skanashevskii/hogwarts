package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Schema(description = "Информация о студенте")
@Entity
@Table(name = "student")
public class Student {

    @Schema(description = "Идентификатор студента")
    @Min(1)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Имя студента")
    @NotBlank//не пустая строка
    private String name;
    @Schema(description = "Возраст студента")
    @Min(1)
    private int age;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    @JsonIgnore
    private Faculty faculty;

    public Student(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Student() {
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Faculty getFaculty() {
        return faculty;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
