-- liquibase formatted sql

-- changeset skanash:1
CREATE INDEX idx_student_name ON student(name)

-- changset skanash:2
CREATE INDEX idx_faculty_name_color ON faculty (name,color);
