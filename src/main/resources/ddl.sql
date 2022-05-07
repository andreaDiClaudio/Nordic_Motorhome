/* DDL stands for Data Definition Language, it is used to define the
   STRUCTURE of the database (schema, tables, columns, etc).
 */
CREATE SCHEMA nordic_motorhome;

USE nordic_motorhome;


CREATE TABLE IF NOT EXISTS customer (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                                    first_name VARCHAR(255) NOT NULL, last_name VARCHAR(255) NOT NULL, dob DATE NOT NULL,
                                    email VARCHAR(255) NOT NULL, phone_number VARCHAR(255) NOT NULL);

CREATE TABLE IF NOT EXISTS motorhome_type (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                                            number_of_persons INT NOT NULL, brand VARCHAR(255) NOT NULL, is_luxury BOOLEAN NOT NULL,
                                            base_price INT NOT NULL);

CREATE TABLE IF NOT EXISTS motorhome (license_plate VARCHAR(255) NOT NULL, type INT NOT NULL, PRIMARY KEY(license_plate),
                                        FOREIGN KEY (type) REFERENCES motorhome_type(id));

CREATE TABLE IF NOT EXISTS booking (id INT NOT NULL, motorhome_id VARCHAR(255) NOT NULL, customer_id INT NOT NULL,
                                    date_start DATE NOT NULL, date_end DATE NOT NULL, PRIMARY KEY (id), FOREIGN KEY (motorhome_id)
                                   REFERENCES motorhome(license_plate), FOREIGN KEY (customer_id) REFERENCES customer (id));
