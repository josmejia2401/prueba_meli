DROP TABLE IF EXISTS meli.users;
DROP TABLE IF EXISTS meli.places;
DROP TABLE IF EXISTS meli.sections;
DROP TABLE IF EXISTS meli.shows;
DROP TABLE IF EXISTS meli.functions;
DROP TABLE IF EXISTS meli.seats;
DROP TABLE IF EXISTS meli.reservations;
DROP TABLE IF EXISTS meli.reserve_seat;

DROP SEQUENCE IF EXISTS meli.seq_users;
DROP SEQUENCE IF EXISTS meli.seq_places;
DROP SEQUENCE IF EXISTS meli.seq_sections;
DROP SEQUENCE IF EXISTS meli.seq_shows;
DROP SEQUENCE IF EXISTS meli.seq_functions;
DROP SEQUENCE IF EXISTS meli.seq_seats;
DROP SEQUENCE IF EXISTS meli.seq_reservations;
DROP SEQUENCE IF EXISTS meli.seq_reserve_seat;

DROP SCHEMA IF EXISTS meli;

CREATE SCHEMA IF NOT EXISTS meli;


-- Crear la tabla de Usuarios
CREATE TABLE meli.users (
    id SERIAL PRIMARY KEY,
    dni VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) CHECK (role IN ('admin', 'client')) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE SEQUENCE meli.seq_users
START 1
INCREMENT 1
MINVALUE 1
OWNED BY meli.users.id;

comment on table meli.users is 'Almacena la información de los usuarios que alimentan la plataforma y aquellos que realizan reservas. Incluye un campo para el rol (admin o client).';
comment on column meli.users.id is 'Identificador único del usuario.';
comment on column meli.users.dni is 'DNI del usuario.';
comment on column meli.users.name is 'Nombre del usuario.';
comment on column meli.users.email is 'Correo electrónico del usuario.';
comment on column meli.users.role is 'Rol del usuario (quien carga información o realiza reservas).';
comment on column meli.users.created_at is 'Fecha de creación del registro en el sistema.';


-- Crear la tabla de Lugares
CREATE TABLE meli.places (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE SEQUENCE meli.seq_places
START 1
INCREMENT 1
MINVALUE 1
OWNED BY meli.places.id;

comment on table meli.places is 'Representa los diferentes lugares (teatros, estadios, etc.) donde se llevan a cabo los shows, incluyendo su capacidad y dirección.';
comment on column meli.places.id is 'Identificador único del lugar.';
comment on column meli.places.name is 'Nombre del lugar (teatro, estadio, campo, etc.).';
comment on column meli.places.address is 'Dirección del lugar.';
comment on column meli.places.capacity is 'Capacidad máxima del lugar.';
comment on column meli.places.created_at is 'Fecha de creación del registro en el sistema.';

-- Crear la tabla de Secciones
CREATE TABLE meli.sections (
    id SERIAL PRIMARY KEY,
    place_id INT REFERENCES places(id) ON DELETE CASCADE,
    name VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE SEQUENCE meli.seq_sections
START 1
INCREMENT 1
MINVALUE 1
OWNED BY meli.sections.id;

comment on table meli.sections is 'Define las secciones dentro de un lugar, cada una con un precio específico.';
comment on column meli.sections.id is 'Identificador único de la sección.';
comment on column meli.sections.place_id is ' Identificador del lugar donde se encuentra la sección.';
comment on column meli.sections.name is 'Nombre de la sección (e.g., "Platea", "General").';
comment on column meli.sections.price is 'Precio de las entradas para esta sección.';
comment on column meli.sections.created_at is 'Fecha de creación del registro en el sistema.';


-- Crear la tabla de Shows
CREATE TABLE meli.shows (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    place_id INT REFERENCES meli.places(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE SEQUENCE meli.seq_shows
START 1
INCREMENT 1
MINVALUE 1
OWNED BY meli.shows.id;

comment on table meli.shows is 'Almacena información sobre los shows, incluyendo su nombre, descripción y las fechas de inicio y fin.';
comment on column meli.shows.id is 'Identificador único del show.';
comment on column meli.shows.name is 'Nombre del show.';
comment on column meli.shows.description is 'Descripción del show.';
comment on column meli.shows.start_date is 'Fecha y hora de inicio del show.';
comment on column meli.shows.end_date is 'Fecha y hora de finalización del show.';
comment on column meli.shows.place_id is 'Identificador del lugar donde se presenta el show.';
comment on column meli.shows.created_at is 'Fecha de creación del registro en el sistema.';


-- Crear la tabla de Funciones
CREATE TABLE meli.functions (
    id SERIAL PRIMARY KEY,
    show_id INT REFERENCES meli.shows(id) ON DELETE CASCADE,
    datetime TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE SEQUENCE meli.seq_functions
START 1
INCREMENT 1
MINVALUE 1
OWNED BY meli.functions.id;

comment on table meli.functions is 'Representa las funciones programadas de un show, con fecha y hora específicas.';
comment on column meli.functions.id is 'Identificador único de la función.';
comment on column meli.functions.show_id is 'Identificador del show asociado.';
comment on column meli.functions.datetime is 'Fecha y hora de la función.';
comment on column meli.functions.created_at is 'Fecha de creación del registro en el sistema.';


-- Crear la tabla de Butacas
CREATE TABLE meli.seats (
    id SERIAL PRIMARY KEY,
    section_id INT REFERENCES meli.sections(id) ON DELETE CASCADE,
    number VARCHAR(10),
    availability BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE SEQUENCE meli.seq_seats
START 1
INCREMENT 1
MINVALUE 1
OWNED BY meli.seats.id;

comment on table meli.seats is 'Define las butacas disponibles en cada sección, junto con su disponibilidad (disponible o reservada).';
comment on column meli.seats.id is 'Identificador único de la butaca.';
comment on column meli.seats.section_id is 'Identificador de la sección a la que pertenece la butaca.';
comment on column meli.seats.number is 'Número de la butaca (puede ser NULL si no aplica).';
comment on column meli.seats.availability is 'Estado de disponibilidad (true = disponible, false = reservado).';
comment on column meli.seats.created_at is 'Fecha de creación del registro en el sistema.';

-- Crear la tabla de Reservas
CREATE TABLE meli.reservations (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES meli.users(id) ON DELETE CASCADE,
    show_id INT REFERENCES meli.shows(id) ON DELETE CASCADE,
    function_id INT REFERENCES meli.functions(id) ON DELETE CASCADE,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),--/* CHECK (status IN ('confirmada', 'cancelada')) DEFAULT 'confirmada',*/
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE SEQUENCE meli.seq_reservations
START 1
INCREMENT 1
MINVALUE 1
OWNED BY meli.reservations.id;

comment on table meli.reservations is 'Almacena las reservas realizadas por los usuarios, asociadas a un show y una función, así como el estado de la reserva.';
comment on column meli.reservations.id is 'Identificador único de la reserva.';
comment on column meli.reservations.user_id is 'Identificador del usuario que realiza la reserva.';
comment on column meli.reservations.show_id is 'Identificador del show asociado.';
comment on column meli.reservations.function_id is 'Identificador de la función asociada.';
comment on column meli.reservations.reservation_date is 'Fecha y hora de la reserva.';
comment on column meli.reservations.status is 'Estado de la reserva.';
comment on column meli.reservations.created_at is 'Fecha de creación del registro en el sistema.';

-- Crear la tabla de relación entre Reservas y Butacas
CREATE TABLE meli.reserve_seat (
    id SERIAL PRIMARY KEY,
    reservation_id INT REFERENCES meli.reservations(id) ON DELETE CASCADE,
    seat_id INT REFERENCES seats(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE SEQUENCE meli.seq_reserve_seat
START 1
INCREMENT 1
MINVALUE 1
OWNED BY meli.reserve_seat.id;

comment on table meli.reserve_seat is 'Tabla de relación que vincula las reservas con las butacas específicas que se han reservado.';
comment on column meli.reserve_seat.id is 'Identificador único de la relación.';
comment on column meli.reserve_seat.reservation_id is 'Identificador de la reserva.';
comment on column meli.reserve_seat.seat_id is 'Identificador de la butaca reservada.';
comment on column meli.reservations.created_at is 'Fecha de creación del registro en el sistema.';