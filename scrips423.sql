Составить первый JOIN-запрос, чтобы получить информацию обо всех студентах
 (достаточно получить только имя и возраст студента) школы Хогвартс вместе
 с названиями факультетов.

Составить второй JOIN-запрос, чтобы получить только тех студентов,
 у которых есть аватарки.

-- Запрос 1: Получение информации о студентах и названиях факультетов
SELECT s.name AS student_name, s.age AS student_age, f.name AS faculty_name
FROM student s
JOIN faculty f ON s.faculty_id = f.id
WHERE f.name = 'Gryffindor';
-- Запрос 1: Получение информации о студентах и названиях факультетов
SELECT s.name AS student_name, s.age AS student_age, f.name AS faculty_name
FROM student s
JOIN faculty f ON s.faculty_id = f.id
WHERE f.color = 'Green';
-- Запрос 1: Получение информации о студентах и названиях факультетов
SELECT s.name AS student_name, s.age AS student_age, f.name AS faculty_name
FROM student s
JOIN faculty f ON s.faculty_id = f.id;

-- Запрос 2: Получение информации о студентах с аватарками
SELECT s.name AS student_name, s.age AS student_age
FROM student s
JOIN avatar a ON s.id = a.student_id;