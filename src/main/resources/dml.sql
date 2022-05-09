USE nordic_motorhome;

INSERT INTO customer (first_name, last_name, dob, email, phone_number)
            VALUES
                   ('Nicolas', 'Jan', '1990-08-10', 'nicolas@gmail.com', '+45 00 00 00 '),
                   ('Andrea', 'Di Claudio', '2000-01-01', 'andrea@yahoo.com', '+45 01 01 01'),
                   ('Daniil', 'Strelan', '2000-02-02', 'daniil@altavista.com', '+45 02 02 02');

INSERT INTO motorhome_type (number_of_persons, brand, is_luxury, base_price)
            VALUES
                   (2, 'Ford', false, 25),
                   (2, 'Mercedes', true, 35),
                   (3, 'Fiat', false, 35),
                   (3, 'Mercedes', true, 45),
                   (4, 'Ford', false, 45),
                   (4, 'Mercedes', true, 55),
                   (6, 'Fiat', false, 55),
                   (6, 'Mercedes', true, 65);

INSERT INTO motorhome (license_plate, type)
            VALUES
                    ('AB00001', 1),
                    ('AB00002', 3),
                    ('AB00003', 8),
                    ('AB00004', 6),
                    ('AB00005', 2),
                    ('AB00006', 3),
                    ('AB00007', 5),
                    ('AB00008', 7),
                    ('AB00009', 1),
                    ('AB00010', 1),
                    ('AB00011', 2),
                    ('AB00012', 8),
                    ('AB00013', 7),
                    ('AB00014', 5),
                    ('AB00015', 6),
                    ('AB00016', 4),
                    ('AB00017', 4),
                    ('AB00018', 5),
                    ('AB00019', 2),
                    ('AB00020', 8),
                    ('AB00021', 7),
                    ('AB00022', 6),
                    ('AB00023', 6),
                    ('AB00024', 2),
                    ('AB00025', 4),
                    ('AB00026', 6),
                    ('AB00027', 8),
                    ('AB00028', 2),
                    ('AB00029', 3),
                    ('AB00030', 5),
                    ('AB00031', 2),
                    ('AB00032', 1);