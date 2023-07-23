package ru.hogwarts.school.dto;

public class StudentDTO {
    private Long id;
    private String name;
    private String age;
    private long facultyId;

    public StudentDTO(Long id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }
}
