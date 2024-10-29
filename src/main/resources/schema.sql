DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS places;
DROP TABLE IF EXISTS sections;
DROP TABLE IF EXISTS shows;
DROP TABLE IF EXISTS functions;
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS reserve_seat;

-- Crear la tabla de Usuarios
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    dni VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) CHECK (role IN ('admin', 'client')) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

comment on table users is 'Almacena la información de los usuarios que alimentan la plataforma y aquellos que realizan reservas. Incluye un campo para el rol (admin o client).';
comment on column users.id is 'Identificador único del usuario.';
comment on column users.dni is 'DNI del usuario.';
comment on column users.name is 'Nombre del usuario.';
comment on column users.email is 'Correo electrónico del usuario.';
comment on column users.role is 'Rol del usuario (quien carga información o realiza reservas).';
comment on column users.created_at is 'Fecha de creación del registro en el sistema.';


-- Crear la tabla de Lugares
CREATE TABLE places (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
comment on table places is 'Representa los diferentes lugares (teatros, estadios, etc.) donde se llevan a cabo los shows, incluyendo su capacidad y dirección.';
comment on column places.id is 'Identificador único del lugar.';
comment on column places.name is 'Nombre del lugar (teatro, estadio, campo, etc.).';
comment on column places.address is 'Dirección del lugar.';
comment on column places.capacity is 'Capacidad máxima del lugar.';
comment on column places.created_at is 'Fecha de creación del registro en el sistema.';

-- Crear la tabla de Secciones
CREATE TABLE sections (
    id SERIAL PRIMARY KEY,
    place_id INT REFERENCES places(id) ON DELETE CASCADE,
    name VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
comment on table sections is 'Define las secciones dentro de un lugar, cada una con un precio específico.';
comment on column sections.id is 'Identificador único de la sección.';
comment on column sections.place_id is ' Identificador del lugar donde se encuentra la sección.';
comment on column sections.name is 'Nombre de la sección (e.g., "Platea", "General").';
comment on column sections.price is 'Precio de las entradas para esta sección.';
comment on column sections.created_at is 'Fecha de creación del registro en el sistema.';


-- Crear la tabla de Shows
CREATE TABLE shows (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    place_id INT REFERENCES places(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
comment on table shows is 'Almacena información sobre los shows, incluyendo su nombre, descripción y las fechas de inicio y fin.';
comment on column shows.id is 'Identificador único del show.';
comment on column shows.name is 'Nombre del show.';
comment on column shows.description is 'Descripción del show.';
comment on column shows.start_date is 'Fecha y hora de inicio del show.';
comment on column shows.end_date is 'Fecha y hora de finalización del show.';
comment on column shows.place_id is 'Identificador del lugar donde se presenta el show.';
comment on column shows.created_at is 'Fecha de creación del registro en el sistema.';


-- Crear la tabla de Funciones
CREATE TABLE functions (
    id SERIAL PRIMARY KEY,
    show_id INT REFERENCES shows(id) ON DELETE CASCADE,
    datetime TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
comment on table functions is 'Representa las funciones programadas de un show, con fecha y hora específicas.';
comment on column functions.id is 'Identificador único de la función.';
comment on column functions.show_id is 'Identificador del show asociado.';
comment on column functions.datetime is 'Fecha y hora de la función.';
comment on column shows.created_at is 'Fecha de creación del registro en el sistema.';


-- Crear la tabla de Butacas
CREATE TABLE seats (
    id SERIAL PRIMARY KEY,
    section_id INT REFERENCES sections(id) ON DELETE CASCADE,
    number VARCHAR(10),
    availability BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
comment on table seats is 'Define las butacas disponibles en cada sección, junto con su disponibilidad (disponible o reservada).';
comment on column seats.id is 'Identificador único de la butaca.';
comment on column seats.section_id is 'Identificador de la sección a la que pertenece la butaca.';
comment on column seats.number is 'Número de la butaca (puede ser NULL si no aplica).';
comment on column seats.availability is 'Estado de disponibilidad (true = disponible, false = reservado).';
comment on column seats.created_at is 'Fecha de creación del registro en el sistema.';

-- Crear la tabla de Reservas
CREATE TABLE reservations (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    show_id INT REFERENCES shows(id) ON DELETE CASCADE,
    function_id INT REFERENCES functions(id) ON DELETE CASCADE,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),--/* CHECK (status IN ('confirmada', 'cancelada')) DEFAULT 'confirmada',*/
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
comment on table reservations is 'Almacena las reservas realizadas por los usuarios, asociadas a un show y una función, así como el estado de la reserva.';
comment on column reservations.id is 'Identificador único de la reserva.';
comment on column reservations.user_id is 'Identificador del usuario que realiza la reserva.';
comment on column reservations.show_id is 'Identificador del show asociado.';
comment on column reservations.function_id is 'Identificador de la función asociada.';
comment on column reservations.reservation_date is 'Fecha y hora de la reserva.';
comment on column reservations.status is 'Estado de la reserva.';
comment on column reservations.created_at is 'Fecha de creación del registro en el sistema.';

-- Crear la tabla de relación entre Reservas y Butacas
CREATE TABLE reserve_seat (
    id SERIAL PRIMARY KEY,
    reservation_id INT REFERENCES reservations(id) ON DELETE CASCADE,
    seat_id INT REFERENCES seats(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
comment on table reserve_seat is 'Tabla de relación que vincula las reservas con las butacas específicas que se han reservado.';
comment on column reserve_seat.id is 'Identificador único de la relación.';
comment on column reserve_seat.reservation_id is 'Identificador de la reserva.';
comment on column reserve_seat.seat_id is 'Identificador de la butaca reservada.';
comment on column reservations.created_at is 'Fecha de creación del registro en el sistema.';