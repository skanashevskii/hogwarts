
ALTER TABLE student
ADD CONSTRAINT check_age CHECK (age >=16);

ALTER TABLE IF EXISTS people
ADD CONSTRAINT name_unique UNIQUE(name);

    ALTER TABLE IF EXISTS faculty
    ADD CONSTRAINT faculty_color_unique UNIQUE(name,color);



ALTER TABLE student
ADD COLUMN age SET DEFAULT 20;






