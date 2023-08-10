
ALTER TABLE student
ADD CONSTRAINT check_age CHECK (age >=16);

ALTER TABLE student
ADD COLUMN name_unique UNIQUE (name);

ALTER TABLE faculty
ADD CONSTRAINT faculty_color_unique UNIQUE(name,color);

ALTER TABLE student
ALTER COLUMN age SET DEFAULT 20;






