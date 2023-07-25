package ru.hogwarts.school.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.hogwarts.school.model.Student;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name")
    @Mapping(source = "age",target = "age")

    StudentDTO studentEntityToStudentDTO(Student student);
    @Mapping(source = "id",target = "id")
    @Mapping(source = "name",target = "name")
    @Mapping(source = "age",target = "age")

    Student studentDTOToStudentEntity(StudentDTO studentDTO);
}
