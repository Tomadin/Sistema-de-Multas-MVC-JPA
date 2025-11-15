-- BORRAR y recrear la base de datos 
DROP DATABASE IF EXISTS `integrador2-mvc`;
CREATE SCHEMA IF NOT EXISTS `integrador2-mvc` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `integrador2-mvc`;

-- -----------------------------------------------------
-- Tabla: modelo (padre de marca)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS modelo (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: marca (referencia modelo)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS marca (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(255) DEFAULT NULL,
  modelo_id INT DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX idx_marca_modelo_id (modelo_id),
  CONSTRAINT marca_fk_modelo FOREIGN KEY (modelo_id) REFERENCES modelo(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: vehiculo (referencia marca)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS vehiculo (
  id INT NOT NULL AUTO_INCREMENT,
  color VARCHAR(50) DEFAULT NULL,
  dominio VARCHAR(20) DEFAULT NULL,
  anio INT DEFAULT NULL,
  marca_id INT DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX ux_vehiculo_dominio (dominio),
  INDEX idx_vehiculo_marca_id (marca_id),
  CONSTRAINT vehiculo_fk_marca FOREIGN KEY (marca_id) REFERENCES marca(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: organizacion
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS organizacion (
  id INT NOT NULL AUTO_INCREMENT,
  nombreOrganizacion VARCHAR(45) NOT NULL,
  localidad VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: estadodelacta
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS estadodelacta (
  id INT NOT NULL AUTO_INCREMENT,
  estado VARCHAR(100) DEFAULT NULL,
  descripcion VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: autoridad
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS autoridad (
  dni INT NOT NULL,
  nombre VARCHAR(45) NOT NULL,
  apellido VARCHAR(45) NOT NULL,
  genero VARCHAR(45) NOT NULL,
  idPlaca INT NOT NULL,
  idLegajo INT NOT NULL,
  PRIMARY KEY (dni)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: conductor
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS conductor (
  dni INT NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  apellido VARCHAR(50) NOT NULL,
  genero VARCHAR(20) DEFAULT NULL,
  domicilio VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (dni)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: licencia (referencia conductor)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS licencia (
  id INT NOT NULL AUTO_INCREMENT,
  numero_licencia INT DEFAULT NULL,
  fecha_vencimiento DATE DEFAULT NULL,
  puntos INT DEFAULT NULL,
  conductor_dni INT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX ux_licencia_numero (numero_licencia),
  INDEX idx_licencia_conductor_dni (conductor_dni),
  CONSTRAINT licencia_fk_conductor FOREIGN KEY (conductor_dni) REFERENCES conductor(dni)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: tiporuta
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tiporuta (
  id INT NOT NULL AUTO_INCREMENT,
  estado VARCHAR(100) DEFAULT NULL,
  tipo VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: ruta (referencia tiporuta)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ruta (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(255) DEFAULT NULL,
  kilometro VARCHAR(50) DEFAULT NULL,
  tipo_ruta_id INT DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX idx_ruta_tipo_ruta_id (tipo_ruta_id),
  CONSTRAINT ruta_fk_tiporuta FOREIGN KEY (tipo_ruta_id) REFERENCES tiporuta(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: actadeconstatacion (referencia muchas tablas)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS actadeconstatacion (
  id INT NOT NULL AUTO_INCREMENT,
  fecha_constatacion DATE DEFAULT NULL,
  fecha_vencimiento_acta DATE DEFAULT NULL,
  hora_constatacion DATETIME DEFAULT NULL,
  lugar VARCHAR(255) DEFAULT NULL,
  observaciones TEXT DEFAULT NULL,
  importe_total DOUBLE DEFAULT NULL,
  organizacion_id INT DEFAULT NULL,
  vehiculo_id INT DEFAULT NULL,
  estado_acta_id INT DEFAULT NULL,
  autoridad_id INT DEFAULT NULL,
  licencia_id INT DEFAULT NULL,
  ruta_id INT DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX idx_acta_organizacion_id (organizacion_id),
  INDEX idx_acta_vehiculo_id (vehiculo_id),
  INDEX idx_acta_estado_acta_id (estado_acta_id),
  INDEX idx_acta_autoridad_id (autoridad_id),
  INDEX idx_acta_licencia_id (licencia_id),
  INDEX idx_acta_ruta_id (ruta_id),
  CONSTRAINT acta_fk_organizacion FOREIGN KEY (organizacion_id) REFERENCES organizacion(id),
  CONSTRAINT acta_fk_vehiculo FOREIGN KEY (vehiculo_id) REFERENCES vehiculo(id),
  CONSTRAINT acta_fk_estado FOREIGN KEY (estado_acta_id) REFERENCES estadodelacta(id),
  CONSTRAINT acta_fk_autoridad FOREIGN KEY (autoridad_id) REFERENCES autoridad(dni),
  CONSTRAINT acta_fk_licencia FOREIGN KEY (licencia_id) REFERENCES licencia(id),
  CONSTRAINT acta_fk_ruta FOREIGN KEY (ruta_id) REFERENCES ruta(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: infraccion
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS infraccion (
  id INT NOT NULL AUTO_INCREMENT,
  descripcion VARCHAR(255) DEFAULT NULL,
  importeInfraccion DOUBLE DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: tipodeinfraccion
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tipodeinfraccion (
  id_infrac INT NOT NULL AUTO_INCREMENT,
  descripcionInfraccion VARCHAR(255) DEFAULT NULL,
  tipoGravedad VARCHAR(50) DEFAULT NULL,
  importeAsignadoInfraccion DOUBLE DEFAULT NULL,
  porcentajeDescuento DOUBLE DEFAULT NULL,
  PRIMARY KEY (id_infrac)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: infraccion_tipoinfraccion (N:N infraccion <-> tipodeinfraccion)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS infraccion_tipoinfraccion (
  infraccion_id INT NOT NULL,
  tipo_infraccion_id INT NOT NULL,
  PRIMARY KEY (infraccion_id, tipo_infraccion_id),
  INDEX idx_it_tipo_infraccion_id (tipo_infraccion_id),
  CONSTRAINT it_fk_infraccion FOREIGN KEY (infraccion_id) REFERENCES infraccion(id) ON DELETE CASCADE,
  CONSTRAINT it_fk_tipodeinfraccion FOREIGN KEY (tipo_infraccion_id) REFERENCES tipodeinfraccion(id_infrac) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: actadeconstatacion_infraccion (N:N acta <-> infraccion)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS actadeconstatacion_infraccion (
  acta_id INT NOT NULL,
  infraccion_id INT NOT NULL,
  PRIMARY KEY (acta_id, infraccion_id),
  INDEX idx_ac_infraccion_id (infraccion_id),
  CONSTRAINT aci_fk_acta FOREIGN KEY (acta_id) REFERENCES actadeconstatacion(id) ON DELETE CASCADE,
  CONSTRAINT aci_fk_infraccion FOREIGN KEY (infraccion_id) REFERENCES infraccion(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Tabla: persona (independiente)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS persona (
  dni INT NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  apellido VARCHAR(100) NOT NULL,
  genero VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (dni)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




-- Registros

USE `integrador2-mvc`;

-- -----------------------------------------------------
-- Tabla: organizacion
-- -----------------------------------------------------
INSERT INTO `organizacion` (id, nombreOrganizacion, localidad) VALUES
(1,'Policia Mendoza','Mendoza'),
(2,'Municipalidad Ciudad','Ciudad'),
(3,'Transito Zona Este','Zona Este'),
(4,'Policia Federal','Buenos Aires'),
(5,'Gendarmeria Nacional','Cordoba'),
(6,'Municipalidad Oeste','Zona Oeste'),
(7,'Transito Sur','Sur'),
(8,'Policia Seguridad Vial','Santa Fe'),
(9,'Policia Metropolitana','CABA'),
(10,'Municipalidad Norte','Norte');

-- -----------------------------------------------------
-- Tabla: modelo
-- -----------------------------------------------------
INSERT INTO `modelo` (id, nombre) VALUES
(1,'Zafiro 1000'),
(2,'Titan XR'),
(3,'Nova Compact'),
(4,'EcoDrive 200'),
(5,'Thunder Pro'),
(6,'StreetRunner 150'),
(7,'AeroSport 500'),
(8,'TurboMax 3000'),
(9,'RoadMaster 900'),
(10,'UrbanLine 250');

-- -----------------------------------------------------
-- Tabla: marca
-- -----------------------------------------------------
INSERT INTO `marca` (id, nombre, modelo_id) VALUES
(1,'Honda',1),
(2,'Yamaha',3),
(3,'Suzuki',5),
(4,'Zanella',2),
(5,'Motomel',4),
(6,'Bajaj',7),
(7,'Gilera',6),
(8,'KTM',8),
(9,'Kawasaki',10),
(10,'Benelli',9);

-- -----------------------------------------------------
-- Tabla: vehiculo
-- -----------------------------------------------------
INSERT INTO `vehiculo` (id, color, dominio, anio, marca_id) VALUES
(1,'Rojo','ABC123',2010,1),
(2,'Azul','DEF456',2012,2),
(3,'Negro','GHI789',2015,3),
(4,'Blanco','JKL012',2018,4),
(5,'Gris','MNO345',2011,5),
(6,'Verde','PQR678',2016,6),
(7,'Amarillo','STU901',2013,7),
(8,'Azul','VWX234',2017,8),
(9,'Negro','YZA567',2019,9),
(10,'Rojo','BCD890',2020,10);

-- -----------------------------------------------------
-- Tabla: estadodelacta
-- -----------------------------------------------------
INSERT INTO `estadodelacta` (id, estado, descripcion) VALUES
(1,'Pendiente','Acta pendiente de revisar'),
(2,'En Proceso','Acta en proceso de revisión'),
(3,'Finalizado','Acta finalizada'),
(4,'Archivado','Acta archivada'),
(5,'Anulado','Acta anulada'),
(6,'En Espera','Acta en espera de datos'),
(7,'Rechazado','Acta rechazada'),
(8,'Aprobado','Acta aprobada'),
(9,'Observado','Acta observada'),
(10,'Confirmado','Acta confirmada');

-- -----------------------------------------------------
-- Tabla: autoridad
-- -----------------------------------------------------
INSERT INTO `autoridad` (dni, nombre, apellido, genero, idPlaca, idLegajo) VALUES
(30123456,'Juan','Pérez','hombre',1200,5010),
(28987654,'María','Gómez','mujer',1210,5011),
(33444555,'Luis','Fernández','hombre',1220,5012),
(32111222,'Ana','Romero','mujer',1230,5013),
(29888777,'Jorge','Herrera','hombre',1240,5014),
(31222999,'Sofía','Díaz','mujer',1250,5015),
(27654321,'Pedro','Martínez','hombre',1260,5016),
(30555999,'Laura','Sosa','mujer',1270,5017),
(33123012,'Nicolás','Rivas','hombre',1280,5018),
(29987123,'Carla','Molina','mujer',1290,5019);

-- -----------------------------------------------------
-- Tabla: conductor
-- -----------------------------------------------------
INSERT INTO `conductor` (dni, nombre, apellido, genero, domicilio) VALUES
(30123456, 'Juan', 'Perez', 'Masculino', 'Calle Falsa 123'),
(28987654, 'Maria', 'Gomez', 'Femenino', 'Av. Libertador 456'),
(33444555, 'Luis', 'Fernandez', 'Masculino', 'Calle 9 de Julio 789'),
(32111222, 'Ana', 'Romero', 'Femenino', 'Av. San Martin 101'),
(29888777, 'Jorge', 'Herrera', 'Masculino', 'Calle Mendoza 202'),
(31222999, 'Sofía', 'Díaz', 'Femenino', 'Av. Belgrano 303'),
(27654321, 'Pedro', 'Martínez', 'Masculino', 'Calle Cordoba 404'),
(30555999, 'Laura', 'Sosa', 'Femenino', 'Av. Mitre 505'),
(33123012, 'Nicolás', 'Rivas', 'Masculino', 'Calle Rivadavia 606'),
(29987123, 'Carla', 'Molina', 'Femenino', 'Av. Libertad 707');

-- -----------------------------------------------------
-- Tabla: persona
-- -----------------------------------------------------
INSERT INTO `persona` (dni, nombre, apellido, genero) VALUES
(30123456,'Juan','Pérez','hombre'),
(28987654,'María','Gómez','mujer'),
(33444555,'Luis','Fernández','hombre'),
(32111222,'Ana','Romero','mujer'),
(29888777,'Jorge','Herrera','hombre'),
(31222999,'Sofía','Díaz','mujer'),
(27654321,'Pedro','Martínez','hombre'),
(30555999,'Laura','Sosa','mujer'),
(33123012,'Nicolás','Rivas','hombre'),
(29987123,'Carla','Molina','mujer');

-- -----------------------------------------------------
-- Tabla: licencia
-- -----------------------------------------------------
INSERT INTO `licencia` (id, numero_licencia, fecha_vencimiento, puntos, conductor_dni) VALUES
(1,10001,'2026-12-31',20,30123456),
(2,10002,'2025-11-30',18,28987654),
(3,10003,'2027-01-15',25,33444555),
(4,10004,'2026-05-10',22,32111222),
(5,10005,'2025-09-20',19,29888777),
(6,10006,'2026-08-05',21,31222999),
(7,10007,'2027-03-30',24,27654321),
(8,10008,'2025-12-01',20,30555999),
(9,10009,'2026-10-10',23,33123012),
(10,10010,'2027-07-15',25,29987123);

-- -----------------------------------------------------
-- Tabla: tiporuta
-- -----------------------------------------------------
INSERT INTO `tiporuta` (id, estado, tipo) VALUES
(1,'Activo','Urbana'),
(2,'Activo','Rural'),
(3,'En Mantenimiento','Autopista'),
(4,'Activo','Carretera'),
(5,'Cerrado','Urbana'),
(6,'En Construccion','Rural'),
(7,'Activo','Autopista'),
(8,'Activo','Carretera'),
(9,'En Mantenimiento','Ruta Provincial'),
(10,'Activo','Ruta Nacional');

-- -----------------------------------------------------
-- Tabla: ruta
-- -----------------------------------------------------
INSERT INTO `ruta` (id, nombre, kilometro, tipo_ruta_id) VALUES
(1,'Ruta 40', 1200, 4),
(2,'Ruta 7', 350, 2),
(3,'Avenida Libertador', 15, 1),
(4,'Autopista del Sol', 200, 3),
(5,'Camino Real', 500, 6),
(6,'Ruta Provincial 5', 150, 9),
(7,'Ruta Nacional 9', 800, 10),
(8,'Calle Principal', 10, 1),
(9,'Ruta del Este', 600, 2),
(10,'Carretera Central', 300, 4);

-- -----------------------------------------------------
-- Tabla: infraccion
-- -----------------------------------------------------
INSERT INTO `infraccion` (id, descripcion, importeInfraccion) VALUES
(1,'Exceso de velocidad',300),
(2,'No usar cinturón',200),
(3,'Documento vencido',150),
(4,'Estacionamiento indebido',180),
(5,'Conducir bajo efectos',500),
(6,'Pasar semáforo en rojo',400),
(7,'Falta de seguro obligatorio',350),
(8,'Falta de luces',100),
(9,'Exceso de carga',250),
(10,'Obstruir vía pública',220);

-- -----------------------------------------------------
-- Tabla: tipodeinfraccion
-- -----------------------------------------------------
INSERT INTO `tipodeinfraccion` (id_infrac, descripcionInfraccion, tipoGravedad, importeAsignadoInfraccion, porcentajeDescuento) VALUES
(1,'Leve exceso de velocidad','Leve',150,10),
(2,'Falta de cinturón de seguridad','Leve',100,5),
(3,'Documento vencido','Media',200,15),
(4,'Estacionamiento indebido','Leve',180,10),
(5,'Conducir bajo efectos','Grave',500,0),
(6,'Pasar semáforo en rojo','Grave',400,0),
(7,'Falta de seguro obligatorio','Media',350,5),
(8,'Falta de luces','Leve',100,10),
(9,'Exceso de carga','Media',250,5),
(10,'Obstruir vía pública','Leve',220,10);

-- -----------------------------------------------------
-- Tabla: actadeconstatacion
-- -----------------------------------------------------
INSERT INTO `actadeconstatacion` (id, fecha_constatacion, fecha_vencimiento_acta, hora_constatacion, lugar, observaciones, importe_total, organizacion_id, vehiculo_id, estado_acta_id, autoridad_id, licencia_id, ruta_id) VALUES
(1,'2025-11-01','2026-11-01','2025-11-01 09:00:00','Mendoza Centro','Sin observaciones',1500,1,1,1,30123456,1,1),
(2,'2025-11-02','2026-11-02','2025-11-02 10:30:00','Ciudad Este','Leve exceso de velocidad',1600,2,2,2,28987654,2,2),
(3,'2025-11-03','2026-11-03','2025-11-03 11:00:00','Zona Sur','Documento vencido',1700,3,3,3,33444555,3,3),
(4,'2025-11-04','2026-11-04','2025-11-04 12:15:00','Barrio Norte','Falta de cinturón',1800,4,4,4,32111222,4,4),
(5,'2025-11-05','2026-11-05','2025-11-05 13:30:00','Ciudad Oeste','Parada indebida',1900,5,5,5,29888777,5,5),
(6,'2025-11-06','2026-11-06','2025-11-06 14:45:00','Zona Este','Exceso de carga',2000,6,6,6,31222999,6,6),
(7,'2025-11-07','2026-11-07','2025-11-07 15:00:00','Centro Sur','Falta de seguro',2100,7,7,7,27654321,7,7),
(8,'2025-11-08','2026-11-08','2025-11-08 16:30:00','Barrio Norte','Señalización incorrecta',2200,8,8,8,30555999,8,8),
(9,'2025-11-09','2026-11-09','2025-11-09 17:00:00','Zona Oeste','Estacionamiento en lugar prohibido',2300,9,9,9,33123012,9,9),
(10,'2025-11-10','2026-11-10','2025-11-10 18:15:00','Ciudad Centro','Exceso de velocidad',2400,10,10,10,29987123,10,10);

-- -----------------------------------------------------
-- Tabla: actadeconstatacion_infraccion
-- -----------------------------------------------------
INSERT INTO `actadeconstatacion_infraccion` (acta_id, infraccion_id) VALUES
(1,1),(1,2),(2,3),(2,4),(3,5),
(3,6),(4,7),(4,8),(5,9),(5,10),
(6,1),(6,3),(7,2),(7,5),(8,4),
(8,6),(9,7),(9,8),(10,9),(10,10);

-- -----------------------------------------------------
-- Tabla: infraccion_tipoinfraccion
-- -----------------------------------------------------
INSERT INTO `infraccion_tipoinfraccion` (infraccion_id, tipo_infraccion_id) VALUES
(1,1),(2,2),(3,3),(4,4),(5,5),
(6,6),(7,7),(8,8),(9,9),(10,10);
