USE school_erp;

DROP TEMPORARY TABLE IF EXISTS first_names;
CREATE TEMPORARY TABLE first_names (name VARCHAR(50));
INSERT INTO first_names (name) VALUES
('James'),('Mary'),('John'),('Patricia'),('Robert'),('Jennifer'),('Michael'),('Linda'),
('William'),('Elizabeth'),('David'),('Barbara'),('Richard'),('Susan'),('Joseph'),('Jessica'),
('Thomas'),('Sarah'),('Charles'),('Karen'),('Aarav'),('Diya'),('Vivaan'),('Ananya'),
('Arjun'),('Ishita'),('Rohan'),('Priya'),('Kabir'),('Meera'),('Aditya'),('Sneha'),
('Sanjay'),('Neha'),('Rahul'),('Pooja'),('Vikram'),('Anjali'),('Karan'),('Riya');

DROP TEMPORARY TABLE IF EXISTS last_names;
CREATE TEMPORARY TABLE last_names (name VARCHAR(50));
INSERT INTO last_names (name) VALUES
('Smith'),('Johnson'),('Williams'),('Brown'),('Jones'),('Garcia'),('Miller'),('Davis'),
('Rodriguez'),('Martinez'),('Hernandez'),('Lopez'),('Gonzalez'),('Wilson'),('Anderson'),
('Sharma'),('Verma'),('Gupta'),('Kumar'),('Singh'),('Patel'),('Reddy'),('Nair'),
('Iyer'),('Rao'),('Mehta'),('Chopra'),('Malhotra'),('Kapoor'),('Joshi');

DROP TEMPORARY TABLE IF EXISTS cities;
CREATE TEMPORARY TABLE cities (name VARCHAR(50));
INSERT INTO cities (name) VALUES
('Springfield'),('Riverton'),('Fairview'),('Greenville'),('Madison'),('Georgetown'),
('Salem'),('Franklin'),('Clinton'),('Arlington'),('Pune'),('Mumbai'),('Delhi'),('Bangalore');

DROP TEMPORARY TABLE IF EXISTS streets;
CREATE TEMPORARY TABLE streets (name VARCHAR(50));
INSERT INTO streets (name) VALUES
('Main St'),('Park Ave'),('Oak Dr'),('Maple Rd'),('Cedar Ln'),('Elm St'),
('Pine St'),('Church Rd'),('Lake View'),('Hill Top'),('Sunset Blvd'),('River Side');

DROP TEMPORARY TABLE IF EXISTS subjects;
CREATE TEMPORARY TABLE subjects (name VARCHAR(50));
INSERT INTO subjects (name) VALUES
('Mathematics'),('Physics'),('Chemistry'),('Biology'),('English'),
('History'),('Geography'),('Computer Science'),('Economics'),('Art');


DELIMITER //

DROP PROCEDURE IF EXISTS generate_teachers //
CREATE PROCEDURE generate_teachers(IN num_rows INT)
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE fname VARCHAR(50);
    DECLARE lname VARCHAR(50);
    DECLARE subj VARCHAR(50);
    DECLARE phone VARCHAR(10);
    DECLARE email VARCHAR(150);
    DECLARE qual VARCHAR(10);

    WHILE i <= num_rows DO
        SELECT name INTO fname FROM first_names ORDER BY RAND() LIMIT 1;
        SELECT name INTO lname FROM last_names ORDER BY RAND() LIMIT 1;
        SELECT name INTO subj FROM subjects ORDER BY RAND() LIMIT 1;

        SET phone = CONCAT(
            ELT(FLOOR(1 + RAND()*4), '6','7','8','9'),
            LPAD(FLOOR(RAND()*1000000000), 9, '0')
        );

        SET email = CONCAT(LOWER(fname), '.', LOWER(lname), i, '@',
            ELT(FLOOR(1 + RAND()*5), 'example.com','mail.com','schoolmail.com','testmail.org','webmail.com'));

        SET qual = ELT(FLOOR(1 + RAND()*5), 'B.Ed','M.Ed','M.Sc','M.A','Ph.D');

        INSERT INTO teachers (name, employee_id, subject, email, phone, qualification)
        VALUES (CONCAT(fname, ' ', lname), CONCAT('EMP', LPAD(i, 5, '0')), subj, email, phone, qual);

        SET i = i + 1;
    END WHILE;
END //

DROP PROCEDURE IF EXISTS generate_courses //
CREATE PROCEDURE generate_courses(IN num_rows INT, IN num_teachers INT)
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE subj VARCHAR(50);
    DECLARE level VARCHAR(20);
    DECLARE tid INT;
    DECLARE credit INT;

    WHILE i <= num_rows DO
        SELECT name INTO subj FROM subjects ORDER BY RAND() LIMIT 1;
        SET level = ELT(FLOOR(1 + RAND()*5), 'I','II','III','Advanced','Basics');
        SET tid = FLOOR(1 + RAND()*num_teachers);
        SET credit = FLOOR(1 + RAND()*5);

        INSERT INTO courses (course_name, course_code, teacher_id, credits)
        VALUES (CONCAT(subj, ' ', level), CONCAT('CRS', LPAD(i, 4, '0')), tid, credit);

        SET i = i + 1;
    END WHILE;
END //

DROP PROCEDURE IF EXISTS generate_students //
CREATE PROCEDURE generate_students(IN num_rows INT)
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE fname VARCHAR(50);
    DECLARE lname VARCHAR(50);
    DECLARE class_name VARCHAR(10);
    DECLARE phone VARCHAR(10);
    DECLARE email VARCHAR(150);
    DECLARE addr VARCHAR(200);
    DECLARE city VARCHAR(50);
    DECLARE street VARCHAR(50);

    WHILE i <= num_rows DO
        SELECT name INTO fname FROM first_names ORDER BY RAND() LIMIT 1;
        SELECT name INTO lname FROM last_names ORDER BY RAND() LIMIT 1;
        SELECT name INTO city FROM cities ORDER BY RAND() LIMIT 1;
        SELECT name INTO street FROM streets ORDER BY RAND() LIMIT 1;

        SET class_name = CONCAT(FLOOR(1 + RAND()*12), '-', ELT(FLOOR(1 + RAND()*4), 'A','B','C','D'));

        SET phone = CONCAT(
            ELT(FLOOR(1 + RAND()*4), '6','7','8','9'),
            LPAD(FLOOR(RAND()*1000000000), 9, '0')
        );

        SET email = CONCAT(LOWER(fname), '.', LOWER(lname), i, '@',
            ELT(FLOOR(1 + RAND()*5), 'example.com','mail.com','schoolmail.com','testmail.org','webmail.com'));

        SET addr = CONCAT(FLOOR(1 + RAND()*999), ' ', street, ', ', city, ' - ', FLOOR(100000 + RAND()*899999));

        INSERT INTO students (name, roll_no, class, email, phone, address)
        VALUES (CONCAT(fname, ' ', lname), CONCAT('STU', LPAD(i, 6, '0')), class_name, email, phone, addr);

        -- Commit every 1000 rows to avoid long-running transactions/timeouts
        IF i % 1000 = 0 THEN
            COMMIT;
            SELECT CONCAT(i, ' / ', num_rows, ' students inserted...') AS progress;
        END IF;

        SET i = i + 1;
    END WHILE;

    COMMIT;
END //

DROP PROCEDURE IF EXISTS generate_attendance //
CREATE PROCEDURE generate_attendance(IN num_students INT, IN num_days INT)
BEGIN
    DECLARE s INT DEFAULT 1;
    DECLARE d INT DEFAULT 0;
    DECLARE att_date DATE;
    DECLARE rnd INT;
    DECLARE att_status VARCHAR(10);
    DECLARE total_inserted INT DEFAULT 0;

    WHILE s <= num_students DO
        SET d = 0;
        WHILE d < num_days DO
            SET att_date = DATE_SUB(CURDATE(), INTERVAL d DAY);

            -- Skip weekends to mimic a real school calendar (Sunday=1, Saturday=7)
            IF DAYOFWEEK(att_date) NOT IN (1, 7) THEN
                SET rnd = FLOOR(1 + RAND()*100);
                SET att_status = CASE
                    WHEN rnd <= 85 THEN 'Present'
                    WHEN rnd <= 95 THEN 'Absent'
                    ELSE 'Late'
                END;

                INSERT INTO attendance (student_id, date, status)
                VALUES (s, att_date, att_status);

                SET total_inserted = total_inserted + 1;

                IF total_inserted % 5000 = 0 THEN
                    COMMIT;
                    SELECT CONCAT(total_inserted, ' attendance records inserted...') AS progress;
                END IF;
            END IF;

            SET d = d + 1;
        END WHILE;

        SET s = s + 1;
    END WHILE;

    COMMIT;
END //

DELIMITER ;


SET GLOBAL wait_timeout = 28800;
SET GLOBAL interactive_timeout = 28800;
SET GLOBAL net_read_timeout = 600;
SET GLOBAL net_write_timeout = 600;


SET autocommit = 0;
SET unique_checks = 0;
SET foreign_key_checks = 0;

CALL generate_teachers(500);
COMMIT;

CALL generate_courses(100, 500);
COMMIT;

CALL generate_students(10000);
COMMIT;

CALL generate_attendance(10000, 30);
COMMIT;

SET unique_checks = 1;
SET foreign_key_checks = 1;
SET autocommit = 1;


DROP PROCEDURE IF EXISTS generate_teachers;
DROP PROCEDURE IF EXISTS generate_courses;
DROP PROCEDURE IF EXISTS generate_students;
DROP PROCEDURE IF EXISTS generate_attendance;

DROP TEMPORARY TABLE IF EXISTS first_names;
DROP TEMPORARY TABLE IF EXISTS last_names;
DROP TEMPORARY TABLE IF EXISTS cities;
DROP TEMPORARY TABLE IF EXISTS streets;
DROP TEMPORARY TABLE IF EXISTS subjects;


SELECT COUNT(*) AS total_teachers FROM teachers;
SELECT COUNT(*) AS total_courses FROM courses;
SELECT COUNT(*) AS total_students FROM students;
SELECT COUNT(*) AS total_attendance FROM attendance;