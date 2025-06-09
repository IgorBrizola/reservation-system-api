CREATE TABLE users (
	id INT IDENTITY PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL,
	password VARCHAR(255) NOT NULL,
);

CREATE TABLE status_tables (
    id INT IDENTITY PRIMARY KEY,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE tables (
	id INT IDENTITY PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	capacity INT NOT NULL,
	id_status INT NOT NULL,
	CONSTRAINT FK_Status_Tables FOREIGN KEY (id_status) REFERENCES status_tables(id)
);

CREATE TABLE status_reservation (
    id INT IDENTITY PRIMARY KEY,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE reservations (
	id INT IDENTITY PRIMARY KEY,
	user_id INT NOT NULL,
	table_id INT NOT NULL,
	date_reservation DATETIME2 NOT NULL,
    id_status INT NOT NULL,
	CONSTRAINT FK_Status_Reservation FOREIGN KEY (id_status) REFERENCES status_reservation(id),
	CONSTRAINT FK_User_id FOREIGN KEY (user_id) REFERENCES users(id),
	CONSTRAINT FK_Table_id FOREIGN KEY (table_id) REFERENCES tables(id)
);