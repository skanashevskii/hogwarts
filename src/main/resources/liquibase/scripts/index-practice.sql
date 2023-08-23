--liquibase formatted sql

--changeset skanash:1
--name: Create student name index
--description: This change adds an index on the 'name' column of the 'student' table
CREATE INDEX idx_student_name ON student(name)

--changeset skanash:2
CREATE INDEX idx_faculty_name_color ON faculty (name,color);
