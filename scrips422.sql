/*В этом задании по описанию необходимо спроектировать таблицы,
 связи между ними и корректно определить типы данных. Здесь не важно,
 какой тип вы выберете, например, для данных, представленных
 в виде строки (varchar или text). Важно, что вы выберете один из строковых типов,
 а не числовых, например.

Описание структуры: у каждого человека есть машина.
    Причем несколько человек могут пользоваться одной машиной.
    У каждого человека есть имя, возраст и признак того,
    что у него есть права (или их нет). У каждой машины есть марка, модель и стоимость.
    Также не забудьте добавить таблицам первичные ключи и связать их.*/

-- Создание таблицы "Люди"
CREATE TABLE People (
    person_id INT PRIMARY KEY,
    name VARCHAR (255) NOT NULL,
    age INT NOT NULL,
    has_license BOOLEAN
);

-- Создание таблицы "Машины"
CREATE TABLE Cars (
    car_id INT PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2)
);

-- Создание таблицы "Связь Люди-Машины"
CREATE TABLE PeopleCars (
    person_car_id INT PRIMARY KEY,
    person_id INT,
    car_id INT,
    FOREIGN KEY (person_id) REFERENCES People(person_id),
    FOREIGN KEY (car_id) REFERENCES Cars(car_id)
);
