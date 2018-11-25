DROP SCHEMA IF EXISTS sakila;
CREATE SCHEMA sakila;
USE sakila;


CREATE TABLE actor (
 actor_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
 first_name VARCHAR NOT NULL,
 last_name VARCHAR NOT NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (actor_id),
 KEY idx_actor_last_name (last_name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE address (
 address_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
 address VARCHAR NOT NULL,
 address2 VARCHAR NULL,
 district VARCHAR NOT NULL,
 city_id SMALLINT UNSIGNED NOT NULL,
 postal_code VARCHAR NULL,
 phone VARCHAR NOT NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (address_id),
 KEY idx_fk_city_id (city_id),
 CONSTRAINT fk_address_city FOREIGN KEY (city_id) REFERENCES address (city_id) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE category (
 category_id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
 name VARCHAR NOT NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (category_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE city (
 city_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
 city VARCHAR NOT NULL,
 country_id SMALLINT UNSIGNED NOT NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (city_id),
 KEY idx_fk_country_id (country_id),
 CONSTRAINT fk_city_country FOREIGN KEY (country_id) REFERENCES city (country_id) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE country (
 country_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
 country VARCHAR NOT NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (country_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE customer (
 customer_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
 store_id TINYINT UNSIGNED NOT NULL,
 first_name VARCHAR NOT NULL,
 last_name VARCHAR NOT NULL,
 email VARCHAR NULL,
 address_id SMALLINT UNSIGNED NOT NULL,
 active BOOLEAN NOT NULL DEFAULT TRUE,
 create_date DATETIME NOT NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (customer_id),
 KEY idx_fk_store_id (store_id),
 KEY idx_fk_address_id (address_id),
 KEY idx_last_name (last_name),
 CONSTRAINT fk_customer_address FOREIGN KEY (address_id) REFERENCES customer (address_id) ON DELETE RESTRICT ON UPDATE CASCADE,
 CONSTRAINT fk_customer_store FOREIGN KEY (store_id) REFERENCES customer (store_id) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE film (
 film_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
 title VARCHAR NOT NULL,
 description TEXT NULL,
 release_year YEAR NULL,
 language_id TINYINT UNSIGNED NOT NULL,
 original_language_id TINYINT UNSIGNED NULL,
 rental_duration TINYINT UNSIGNED NOT NULL DEFAULT 3,
 rental_rate DECIMAL NOT NULL DEFAULT 4.99,
 length SMALLINT UNSIGNED NULL,
 replacement_cost DECIMAL NOT NULL DEFAULT 19.99,
 rating ENUM NULL DEFAULT G,
 special_features SET NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (film_id),
 KEY idx_title (title),
 KEY idx_fk_language_id (language_id),
 KEY idx_fk_original_language_id (original_language_id),
 CONSTRAINT fk_film_language FOREIGN KEY (language_id) REFERENCES film (language_id) ON DELETE RESTRICT ON UPDATE CASCADE,
 CONSTRAINT fk_film_language_original FOREIGN KEY (original_language_id) REFERENCES film (original_language_id) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE film_actor (
 actor_id SMALLINT UNSIGNED NOT NULL,
 film_id SMALLINT UNSIGNED NOT NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (actor_id,film_id),
 KEY idx_fk_film_id (film_id),
 CONSTRAINT fk_film_actor_actor FOREIGN KEY (actor_id) REFERENCES film_actor (actor_id) ON DELETE RESTRICT ON UPDATE CASCADE,
 CONSTRAINT fk_film_actor_film FOREIGN KEY (film_id) REFERENCES film_actor (film_id) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE film_category (
 film_id SMALLINT UNSIGNED NOT NULL,
 category_id TINYINT UNSIGNED NOT NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (category_id,film_id),
 KEY fk_film_category_category (category_id),
 CONSTRAINT fk_film_category_category FOREIGN KEY (category_id) REFERENCES film_category (category_id) ON DELETE RESTRICT ON UPDATE CASCADE,
 CONSTRAINT fk_film_category_film FOREIGN KEY (film_id) REFERENCES film_category (film_id) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE film_text (
 film_id SMALLINT NOT NULL,
 title VARCHAR NOT NULL,
 description TEXT NULL,
 PRIMARY KEY (film_id),
 KEY idx_title_description (title),
 KEY idx_title_description (description)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE inventory (
 inventory_id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
 film_id SMALLINT UNSIGNED NOT NULL,
 store_id TINYINT UNSIGNED NOT NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (inventory_id),
 KEY idx_fk_film_id (film_id),
 KEY idx_store_id_film_id (store_id),
 KEY idx_store_id_film_id (film_id),
 CONSTRAINT fk_inventory_film FOREIGN KEY (film_id) REFERENCES inventory (film_id) ON DELETE RESTRICT ON UPDATE CASCADE,
 CONSTRAINT fk_inventory_store FOREIGN KEY (store_id) REFERENCES inventory (store_id) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE language (
 language_id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
 name CHAR NOT NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (language_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE payment (
 payment_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
 customer_id SMALLINT UNSIGNED NOT NULL,
 staff_id TINYINT UNSIGNED NOT NULL,
 rental_id INT NULL,
 amount DECIMAL NOT NULL,
 payment_date DATETIME NOT NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (payment_id),
 KEY idx_fk_staff_id (staff_id),
 KEY idx_fk_customer_id (customer_id),
 KEY fk_payment_rental (rental_id),
 CONSTRAINT fk_payment_customer FOREIGN KEY (customer_id) REFERENCES payment (customer_id) ON DELETE RESTRICT ON UPDATE CASCADE,
 CONSTRAINT fk_payment_rental FOREIGN KEY (rental_id) REFERENCES payment (rental_id) ON DELETE SET NULL ON UPDATE CASCADE,
 CONSTRAINT fk_payment_staff FOREIGN KEY (staff_id) REFERENCES payment (staff_id) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE rental (
 rental_id INT NOT NULL AUTO_INCREMENT,
 rental_date DATETIME NOT NULL,
 inventory_id MEDIUMINT UNSIGNED NOT NULL,
 customer_id SMALLINT UNSIGNED NOT NULL,
 return_date DATETIME NULL,
 staff_id TINYINT UNSIGNED NOT NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (rental_id),
 UNIQUE KEY (rental_date,inventory_id,customer_id),
 KEY rental_date (rental_date),
 KEY rental_date (inventory_id),
 KEY rental_date (customer_id),
 KEY idx_fk_inventory_id (inventory_id),
 KEY idx_fk_customer_id (customer_id),
 KEY idx_fk_staff_id (staff_id),
 CONSTRAINT fk_rental_customer FOREIGN KEY (customer_id) REFERENCES rental (customer_id) ON DELETE RESTRICT ON UPDATE CASCADE,
 CONSTRAINT fk_rental_inventory FOREIGN KEY (inventory_id) REFERENCES rental (inventory_id) ON DELETE RESTRICT ON UPDATE CASCADE,
 CONSTRAINT fk_rental_staff FOREIGN KEY (staff_id) REFERENCES rental (staff_id) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE staff (
 staff_id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
 first_name VARCHAR NOT NULL,
 last_name VARCHAR NOT NULL,
 address_id SMALLINT UNSIGNED NOT NULL,
 picture BLOB NULL,
 email VARCHAR NULL,
 store_id TINYINT UNSIGNED NOT NULL,
 active BOOLEAN NOT NULL DEFAULT TRUE,
 username VARCHAR NOT NULL,
 password VARCHAR NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (staff_id),
 KEY idx_fk_store_id (store_id),
 KEY idx_fk_address_id (address_id),
 CONSTRAINT fk_staff_address FOREIGN KEY (address_id) REFERENCES staff (address_id) ON DELETE RESTRICT ON UPDATE CASCADE,
 CONSTRAINT fk_staff_store FOREIGN KEY (store_id) REFERENCES staff (store_id) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE store (
 store_id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
 manager_staff_id TINYINT UNSIGNED NOT NULL,
 address_id SMALLINT UNSIGNED NOT NULL,
 last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (store_id),
 UNIQUE KEY (manager_staff_id),
 KEY idx_unique_manager (manager_staff_id),
 KEY idx_fk_address_id (address_id),
 CONSTRAINT fk_store_address FOREIGN KEY (address_id) REFERENCES store (address_id) ON DELETE RESTRICT ON UPDATE CASCADE,
 CONSTRAINT fk_store_staff FOREIGN KEY (manager_staff_id) REFERENCES store (manager_staff_id) ON DELETE RESTRICT ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

