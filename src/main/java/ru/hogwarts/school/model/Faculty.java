package ru.hogwarts.school.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Schema(description = "Информация о факультете")
@Entity
@Table(name = "faculty")
public class Faculty {

    @Schema(description = "Идентификатор факультета")
    @Min(1)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Наименование факультета")
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    @Schema(description = "Цвет факультета")
    @NotBlank//не пустая строка
    @Column(name = "color")
    private String color;

    @OneToMany(mappedBy = "faculty")
    private Set<Student> students;

    public Faculty(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Faculty() {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(id, faculty.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

