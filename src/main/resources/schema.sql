CREATE TABLE IF NOT EXISTS tecnologia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(90) NOT NULL
);

CREATE TABLE IF NOT EXISTS capacidad (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(90) NOT NULL
);

CREATE TABLE IF NOT EXISTS capacidad_tecnologia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    capacidad_id BIGINT NOT NULL,
    tecnologia_id BIGINT NOT NULL,
    UNIQUE (capacidad_id, tecnologia_id),
    FOREIGN KEY (capacidad_id) REFERENCES capacidad(id),
    FOREIGN KEY (tecnologia_id) REFERENCES tecnologia(id)
);

CREATE TABLE IF NOT EXISTS bootcamp (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(90) NOT NULL,
    fecha_lanzamiento DATE NOT NULL,
    duracion INT NOT NULL
);

CREATE TABLE IF NOT EXISTS bootcamp_capacidad (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bootcamp_id BIGINT NOT NULL,
    capacidad_id BIGINT NOT NULL,
    UNIQUE (bootcamp_id, capacidad_id),
    FOREIGN KEY (bootcamp_id) REFERENCES bootcamp(id),
    FOREIGN KEY (capacidad_id) REFERENCES capacidad(id)
);