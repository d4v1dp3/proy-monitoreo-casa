/* ------------------------------------------------------------------------ */
/*  Base de datos para el sistema de Monitore Remoto para pacientes COVID   */
/*  Autor: Iliac Huerta Trujillo			      		    */
/*  Creado : 10-Sep-2020 04:15:34 p.m. 				    	    */
/*  DBMS   : MySql 							    */
/* ------------------------------------------------------------------------ */
CREATE DATABASE sistmr;
USE sismr;

SET FOREIGN_KEY_CHECKS=0
; 
/* Drop Tables */
DROP TABLE IF EXISTS `RM_ANTECEDENTES` CASCADE
;

DROP TABLE IF EXISTS `RM_BITACORA` CASCADE
;

DROP TABLE IF EXISTS `RM_CARETA` CASCADE
;

DROP TABLE IF EXISTS `RM_CARETA_HOSPITAL` CASCADE
;

DROP TABLE IF EXISTS `RM_ESTADOPACIENTE` CASCADE
;

DROP TABLE IF EXISTS `RM_EVENTOBITACORA` CASCADE
;

DROP TABLE IF EXISTS `RM_GENERO` CASCADE
;

DROP TABLE IF EXISTS `RM_HOSPITAL_MEDICO` CASCADE
;

DROP TABLE IF EXISTS `RM_HOSPITAL` CASCADE
;

DROP TABLE IF EXISTS `RM_MEDICO` CASCADE
;

DROP TABLE IF EXISTS `RM_MEDIDAS` CASCADE
;

DROP TABLE IF EXISTS `RM_MENU` CASCADE
;

DROP TABLE IF EXISTS `RM_MENU_OPCION` CASCADE
;

DROP TABLE IF EXISTS `RM_OPCION` CASCADE
;

DROP TABLE IF EXISTS `RM_PACIENTE` CASCADE
;

DROP TABLE IF EXISTS `RM_PACIENTE_MEDICO` CASCADE
;

DROP TABLE IF EXISTS `RM_PERSONA` CASCADE
;

DROP TABLE IF EXISTS `RM_ROL` CASCADE
;

DROP TABLE IF EXISTS `RM_ROL_MENU` CASCADE
;

DROP TABLE IF EXISTS `RM_SINTOMAS` CASCADE
;

DROP TABLE IF EXISTS `RM_USUARIO` CASCADE
;

DROP TABLE IF EXISTS `RM_USUARIO_ROL` CASCADE
;

DROP TABLE IF EXISTS `RM_VALORES_REFERENCIA` CASCADE
;

/* Create Tables */

CREATE TABLE `RM_ANTECEDENTES`
(
	`DIABETES` BOOL NULL,
	`CANCER` BOOL NULL,
	`ID_PACIENTE` BIGINT NOT NULL,
	`ASMA` BOOL NULL,
	`VIH` BOOL NULL,
	`HAS` BOOL NULL,
	`EPOC` BOOL NULL,
	`EMBARAZO` BOOL NULL,
	`ARTRITIS` BOOL NULL,
	`ENFAUTOINMUNE` BOOL NULL,
	`FECHA` DATE NOT NULL,
        CONSTRAINT `PK_RM_ANTECEDENTES` PRIMARY KEY (`ID_PACIENTE` ASC)
)
COMMENT = 'Tabla para control de antecedentes medicos'

;


CREATE TABLE `RM_BITACORA`
(
	`ID_ENTRADA` BIGINT NOT NULL AUTO_INCREMENT,
	`FECHA_ENTRADA` DATE NULL,
	`ID_EVENTO` INT NULL,
	`ID_USUARIO` VARCHAR(20) NULL,
	CONSTRAINT `PK_RM_BITACORA` PRIMARY KEY (`ID_ENTRADA` ASC)
)

;

CREATE TABLE `RM_CARETA`
(
	`ID_CARETA` BIGINT NOT NULL AUTO_INCREMENT,
	`FECHA_MANUFACTURA` DATETIME NOT NULL,
	CONSTRAINT `PK_CARETA` PRIMARY KEY (`ID_CARETA` ASC)
)

;

CREATE TABLE `RM_CARETA_HOSPITAL`
(
	`FECHA_ASIGNACION` VARCHAR(50) NOT NULL,
	`ID_CARETA` BIGINT NOT NULL,
	`ID_HOSPITAL` INT NOT NULL,
	CONSTRAINT `PK_RM_CARETA_HOSPITAL` PRIMARY KEY (`ID_CARETA` ASC, `ID_HOSPITAL` ASC)
)

;

CREATE TABLE `RM_ESTADOPACIENTE`
(
	`ID_ESTADOPACIENTE` SMALLINT NOT NULL,
	`DESCRIPCION` VARCHAR(50) NOT NULL,
	CONSTRAINT `PK_RM_ESTADOPACIENTE` PRIMARY KEY (`ID_ESTADOPACIENTE` ASC)
)

;

CREATE TABLE `RM_EVENTOBITACORA`
(
	`ID_EVENTO` INT NOT NULL,
	`DESCRIPCION` VARCHAR(50) NULL,
	CONSTRAINT `PK_RM_EVENTOBITACORA` PRIMARY KEY (`ID_EVENTO` ASC)
)

;

CREATE TABLE `RM_GENERO`
(
	`ID_GENERO` SMALLINT NOT NULL,
	`DESCRIPCION` VARCHAR(30) NULL,
	CONSTRAINT `PK_RM_GENERO` PRIMARY KEY (`ID_GENERO` ASC)
)
COMMENT = 'Tabla de generos asociados a las personas.'

;

CREATE TABLE `RM_HOSPITAL_MEDICO`
(
	`ID_HOSPITAL` INT NOT NULL,
	`ID_MEDICO` INT NOT NULL,
	CONSTRAINT `PK_RM_HOSPITAL_MEDICO` PRIMARY KEY (`ID_HOSPITAL` ASC, `ID_MEDICO` ASC)
)
COMMENT = 'Tabla de relación medicos con hospitales. 

un médico puede estar en varios hospitales.'

;

CREATE TABLE `RM_HOSPITAL`
(
	`ID_HOSPITAL` INT NOT NULL AUTO_INCREMENT,
	`NOMBRE` VARCHAR(50) NOT NULL,
	`UBICACION_GEO` VARCHAR(50) NOT NULL,
	`TEL_EMERGENCIAS` VARCHAR(50) NULL,
	`DIR_CALLE` VARCHAR(50) NULL,
	`NUMERO` INT NULL,
	`COLONIA` VARCHAR(50) NULL,
	`MUNICIPIO` VARCHAR(50) NULL,
	`ESTADO` VARCHAR(50) NULL,
	CONSTRAINT `PK_HOSPITAL` PRIMARY KEY (`ID_HOSPITAL` ASC)
)
COMMENT = 'Entidad para representar hospitales en los cuales se asocianpacientes y doctores.'

;

CREATE TABLE `RM_MEDICO`
(
	`ID_MEDICO` INT NOT NULL AUTO_INCREMENT,
	`CEDULA_PROF` VARCHAR(10) NOT NULL,
	`ID_PERSONA` INT NOT NULL,
	`EMAIL` VARCHAR(50) NOT NULL,
	`CELULAR` VARCHAR(12) NOT NULL,
	CONSTRAINT `PK_MEDICO` PRIMARY KEY (`ID_MEDICO` ASC)
)
COMMENT = 'Tabla de medicos que pertenecen a un hospital.

Un médico puede estar asociado a más de un hospital. 

Supongo que el administrador se encargará de llevar a cabo esa relación.'

;

CREATE TABLE `RM_MEDIDAS`
(
	`FECHA_MEDICION` DATETIME NOT NULL,
	`SATURACION_OXIGENO` FLOAT(4,2) NOT NULL,
	`TEMPERATURA` FLOAT(4,2) NOT NULL,
	`CAPNOGRAFIA` SMALLINT NOT NULL,
	`FREC_CARDIACA` SMALLINT NOT NULL,
	`FREC_RESPIRATORIA` SMALLINT NOT NULL,
	`ID_PACIENTE` BIGINT NOT NULL,
	`ID_CARETA` BIGINT NOT NULL,
	`ID_MEDICION` BIGINT NOT NULL AUTO_INCREMENT,
	`ALERTA` BOOL NOT NULL DEFAULT FALSE,
    `PRE_ART_SISTOLICA` INT NOT NULL,
	`PRE_ART_DIASTOLICA` INT NOT NULL,
	CONSTRAINT `PK_RM_MEDIDAS` PRIMARY KEY (`ID_MEDICION` ASC, `ID_CARETA` ASC, `ID_PACIENTE` ASC)
)

;

CREATE TABLE `RM_MENU`
(
	`ID_MENU` SMALLINT NOT NULL,
	`DESCRIPCION` VARCHAR(50) NULL,
	`RUTA_ICONO` VARCHAR(100) NULL,
	`POSICION` SMALLINT NULL,
	CONSTRAINT `PK_RM_MENU` PRIMARY KEY (`ID_MENU` ASC)
)
COMMENT = 'Tabla propuesta para generar los menús de forma dinámica'

;

CREATE TABLE `RM_MENU_OPCION`
(
	`ID_MENU_OPCION` INT NOT NULL,
	`ID_MENU` SMALLINT NOT NULL,
	`ID_OPCION` INT NOT NULL,
	`ID_SUB_MENU` SMALLINT NULL,
	`POSICION` SMALLINT NULL,
	CONSTRAINT `PK_RM_MENU_OPCION` PRIMARY KEY (`ID_MENU_OPCION` ASC)
)
COMMENT = 'Tabla que relaciona las opciones que van a conformar a cada menu.'

;

CREATE TABLE `RM_OPCION`
(
	`ID_OPCION` INT NOT NULL,
	`DESCRIPCION` VARCHAR(50) NULL,
	`RUTA_ICONO` VARCHAR(50) NULL,
	`ACCION` VARCHAR(100) NULL,
	CONSTRAINT `PK_RM_OPCION` PRIMARY KEY (`ID_OPCION` ASC)
)
COMMENT = 'Tabla propuesta para la definición de opciones con los que se conformarán los diferentes menús y submenús.'

;

CREATE TABLE `RM_PACIENTE`
(
	`ID_PACIENTE` BIGINT NOT NULL AUTO_INCREMENT,
	`ID_PERSONA` INT NULL,
	`DIR_CALLE` VARCHAR(50) NULL,
	`ID_HOSPITAL` INT NULL,
	`DIR_NUMERO` INT NULL,
	`DIR_INTERIOR` VARCHAR(50) NULL,
	`TEL_FIJO` VARCHAR(12) NULL,
	`TEL_CEL` VARCHAR(12) NULL,
	`ID_CARETA` BIGINT NULL,
	`ID_ESTADOPACIENTE` SMALLINT NULL,
	CONSTRAINT `PK_PACIENTE` PRIMARY KEY (`ID_PACIENTE` ASC)
)
COMMENT = 'Tabla para almacenar los datos generales de un Paciente, se asocia con persona, Hospital y Doctor'

;

CREATE TABLE `RM_PACIENTE_MEDICO`
(
	`FECHA_INICIO` DATETIME NULL,
	`ID_PACIENTE` BIGINT NOT NULL,
	`ID_MEDICO` INT NOT NULL,
	CONSTRAINT `PK_RM_PACIENTE_MEDICO` PRIMARY KEY (`ID_PACIENTE` ASC, `ID_MEDICO` ASC)
)

;

CREATE TABLE `RM_PERSONA`
(
	`ID_PERSONA` INT NOT NULL AUTO_INCREMENT,
	`ID_GENERO` SMALLINT NULL,
	`NOMBRE` VARCHAR(50) NULL,
	`PRIMER_APELLIDO` VARCHAR(50) NULL,
	`SEGUNDO_APELLIDO` VARCHAR(50) NULL,
	`CURP` VARCHAR(50) NULL,
	CONSTRAINT `PK_RM_PERSONA` PRIMARY KEY (`ID_PERSONA` ASC)
)
COMMENT = 'Registro de personas, puede tener o no un rol dentro de la aplicacion'

;

CREATE TABLE `RM_ROL`
(
	`ID_ROL` SMALLINT NOT NULL,
	`DESCRIPCION` VARCHAR(50) NULL,
	CONSTRAINT `PK_ID_ROL` PRIMARY KEY (`ID_ROL` ASC)
)
COMMENT = 'Tabla que guarda la lista de Roles para el sistema'

;

CREATE TABLE `RM_ROL_MENU`
(
	`ID_ROL` SMALLINT NOT NULL,
	`ID_MENU` SMALLINT NOT NULL,
	CONSTRAINT `PK_RM_ROL_MENU` PRIMARY KEY (`ID_ROL` ASC, `ID_MENU` ASC)
)
COMMENT = 'Tlabla que relaciona los menús con los roles.'

;

CREATE TABLE `RM_SINTOMAS`
(
	`FECHA` DATE NOT NULL,
	`ID_PACIENTE` BIGINT NOT NULL,
	`TOS` BOOL NULL,
	`CEFALEA` BOOL NULL,
	`DOLOR_ABDOMINAL` BOOL NULL,
	`DOLOR_TORACICO` BOOL NULL,
	`VOMITO` BOOL NULL,
	`IRRITABLE` BOOL NULL,
	`PERDIDA_OLFATO_GUSTO` BOOL NULL,
	`DIARREA` BOOL NULL,
	`ODINOFAGIA` BOOL NULL,
	`RINORREA` BOOL NULL,
	`MIALGIAS` BOOL NULL,
	CONSTRAINT `PK_RM_SINTOMAS` PRIMARY KEY (`FECHA` ASC, `ID_PACIENTE` ASC)
)

;

CREATE TABLE `RM_USUARIO`
(
	`ID_USUARIO` VARCHAR(20) NOT NULL,
	`CONTRASENIA` VARCHAR(50) NULL,
	`ACTIVO` BOOL NULL,
	`ID_PERSONA` INT NULL,
	CONSTRAINT `PK_RM_USUARIO` PRIMARY KEY (`ID_USUARIO` ASC)
)
COMMENT = 'Tabla para almacenar los usuarios que tienen acceso al sistema, un usuario podría tener más de un rol.'

;

CREATE TABLE `RM_USUARIO_ROL`
(
	`ID_USUARIO` VARCHAR(50) NOT NULL,
	`ID_ROL` SMALLINT NOT NULL,
	CONSTRAINT `PK_RM_USUARIO_ROL` PRIMARY KEY (`ID_USUARIO` ASC, `ID_ROL` ASC)
)

;

CREATE TABLE `RM_VALORES_REFERENCIA`
(
	`SAT_OXIGENO_MIN` FLOAT(5,2) NOT NULL DEFAULT 92.0,
	`SAT_OXIGENO_MAX` FLOAT(5,2) NOT NULL DEFAULT 100.0,
	`TEMPERATURA_MIN` FLOAT(5,2) NOT NULL DEFAULT 35.5,
	`TEMPERATURA_MAX` FLOAT(5,2) NOT NULL DEFAULT 37.5,
	`CAPNOGRAFIA_MIN` SMALLINT NOT NULL DEFAULT 35,
	`CAPNOGRAFIA_MAX` SMALLINT NOT NULL DEFAULT 45,
	`FREC_CARDIACA_MIN` SMALLINT NOT NULL DEFAULT 60,
	`FREC_CARDIACA_MAX` SMALLINT NOT NULL DEFAULT 80,
	`FREC_RESPIRATORIA_MIN` SMALLINT NOT NULL DEFAULT 18,
	`FREC_RESPIRATORIA_MAX` SMALLINT NOT NULL DEFAULT 19,
	`ID_VALREF` SMALLINT NOT NULL,
	CONSTRAINT `PK_RM_VALORES_REFERENCIA` PRIMARY KEY (`ID_VALREF` ASC)
)

;

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE `RM_ANTECEDENTES` 
 ADD INDEX `IXFK_RM_ANTECEDENTES_RM_PACIENTE` (`ID_PACIENTE` ASC)
;

ALTER TABLE `RM_BITACORA` 
 ADD INDEX `IXFK_RM_BITACORA_RM_EVENTOBITACORA` (`ID_EVENTO` ASC)
;

ALTER TABLE `RM_BITACORA` 
 ADD INDEX `IXFK_RM_BITACORA_RM_USUARIO` (`ID_USUARIO` ASC)
;

ALTER TABLE `RM_CARETA_HOSPITAL` 
 ADD INDEX `IXFK_RM_CARETA_HOSPITAL_RM_CARETA` (`ID_CARETA` ASC)
;

ALTER TABLE `RM_CARETA_HOSPITAL` 
 ADD INDEX `IXFK_RM_CARETA_HOSPITAL_RM_HOSPITAL` (`ID_HOSPITAL` ASC)
;

ALTER TABLE `RM_HOSPITAL_MEDICO` 
 ADD INDEX `IXFK_RM_HOSPITAL_MEDICO_RM_HOSPITAL` (`ID_HOSPITAL` ASC)
;

ALTER TABLE `RM_HOSPITAL_MEDICO` 
 ADD INDEX `IXFK_RM_HOSPITAL_MEDICO_RM_MEDICO` (`ID_MEDICO` ASC)
;

ALTER TABLE `RM_MEDICO` 
 ADD INDEX `IXFK_MEDICO_HOSPITAL` (`ID_MEDICO` ASC)
;

ALTER TABLE `RM_MEDICO` 
 ADD INDEX `IXFK_MEDICO_RM_PERSONA` (`ID_PERSONA` ASC)
;

ALTER TABLE `RM_MEDIDAS` 
 ADD INDEX `IXFK_RM_MEDIDAS_RM_CARETA` (`ID_CARETA` ASC)
;

ALTER TABLE `RM_MEDIDAS` 
 ADD INDEX `IXFK_RM_MEDIDAS_RM_PACIENTE` (`ID_PACIENTE` ASC)
;

ALTER TABLE `RM_MENU_OPCION` 
 ADD INDEX `IXFK_RM_MENU_OPCION_RM_MENU` (`ID_MENU` ASC)
;

ALTER TABLE `RM_MENU_OPCION` 
 ADD INDEX `IXFK_RM_MENU_OPCION_RM_MENU_02` (`ID_SUB_MENU` ASC)
;

ALTER TABLE `RM_MENU_OPCION` 
 ADD INDEX `IXFK_RM_MENU_OPCION_RM_OPCION` (`ID_OPCION` ASC)
;

ALTER TABLE `RM_PACIENTE` 
 ADD INDEX `IXFK_PACIENTE_HOSPITAL` (`ID_HOSPITAL` ASC)
;

ALTER TABLE `RM_PACIENTE` 
 ADD INDEX `IXFK_PACIENTE_RM_PERSONA` (`ID_PERSONA` ASC)
;

ALTER TABLE `RM_PACIENTE` 
 ADD INDEX `IXFK_RM_PACIENTE_RM_CARETA` (`ID_CARETA` ASC)
;

ALTER TABLE `RM_PACIENTE` 
 ADD INDEX `IXFK_RM_PACIENTE_RM_ESTADOPACIENTE` (`ID_ESTADOPACIENTE` ASC)
;

ALTER TABLE `RM_PACIENTE_MEDICO` 
 ADD INDEX `IXFK_RM_PACIENTE_MEDICO_RM_MEDICO` (`ID_MEDICO` ASC)
;

ALTER TABLE `RM_PACIENTE_MEDICO` 
 ADD INDEX `IXFK_RM_PACIENTE_MEDICO_RM_PACIENTE` (`ID_PACIENTE` ASC)
;

ALTER TABLE `RM_PERSONA` 
 ADD INDEX `IXFK_RM_PERSONA_RM_GENERO` (`ID_GENERO` ASC)
;

ALTER TABLE `RM_ROL_MENU` 
 ADD INDEX `IXFK_RM_ROL_MENU_RM_MENU` (`ID_MENU` ASC)
;

ALTER TABLE `RM_ROL_MENU` 
 ADD INDEX `IXFK_RM_ROL_MENU_RM_ROL` (`ID_ROL` ASC)
;

ALTER TABLE `RM_SINTOMAS` 
 ADD INDEX `IXFK_RM_SINTOMAS_RM_PACIENTE` (`ID_PACIENTE` ASC)
;

ALTER TABLE `RM_USUARIO` 
 ADD INDEX `IXFK_RM_USUARIO_RM_PERSONA` (`ID_PERSONA` ASC)
;

ALTER TABLE `RM_USUARIO` 
 ADD INDEX `IXID_USUARIO` (`ID_USUARIO` ASC)
;

ALTER TABLE `RM_USUARIO_ROL` 
 ADD INDEX `IXFK_RM_USUARIO_ROL_RM_ROL` (`ID_ROL` ASC)
;

ALTER TABLE `RM_USUARIO_ROL` 
 ADD INDEX `IXFK_RM_USUARIO_ROL_RM_USUARIO` (`ID_USUARIO` ASC)
;

/* Create Foreign Key Constraints */

ALTER TABLE `RM_ANTECEDENTES` 
 ADD CONSTRAINT `FK_RM_ANTECEDENTES_RM_PACIENTE`
	FOREIGN KEY (`ID_PACIENTE`) REFERENCES `RM_PACIENTE` (`ID_PACIENTE`)
;

ALTER TABLE `RM_BITACORA` 
 ADD CONSTRAINT `FK_RM_BITACORA_RM_EVENTOBITACORA`
	FOREIGN KEY (`ID_EVENTO`) REFERENCES `RM_EVENTOBITACORA` (`ID_EVENTO`)
;

ALTER TABLE `RM_BITACORA` 
 ADD CONSTRAINT `FK_RM_BITACORA_RM_USUARIO`
	FOREIGN KEY (`ID_USUARIO`) REFERENCES `RM_USUARIO` (`ID_USUARIO`) 
;

ALTER TABLE `RM_CARETA_HOSPITAL` 
 ADD CONSTRAINT `FK_RM_CARETA_HOSPITAL_RM_CARETA`
	FOREIGN KEY (`ID_CARETA`) REFERENCES `RM_CARETA` (`ID_CARETA`) 
;

ALTER TABLE `RM_CARETA_HOSPITAL` 
 ADD CONSTRAINT `FK_RM_CARETA_HOSPITAL_RM_HOSPITAL`
	FOREIGN KEY (`ID_HOSPITAL`) REFERENCES `RM_HOSPITAL` (`ID_HOSPITAL`) 
;

ALTER TABLE `RM_HOSPITAL_MEDICO` 
 ADD CONSTRAINT `FK_RM_HOSPITAL_MEDICO_RM_HOSPITAL`
	FOREIGN KEY (`ID_HOSPITAL`) REFERENCES `RM_HOSPITAL` (`ID_HOSPITAL`) 
;

ALTER TABLE `RM_HOSPITAL_MEDICO` 
 ADD CONSTRAINT `FK_RM_HOSPITAL_MEDICO_RM_MEDICO`
	FOREIGN KEY (`ID_MEDICO`) REFERENCES `RM_MEDICO` (`ID_MEDICO`) 
;

ALTER TABLE `RM_MEDICO` 
 ADD CONSTRAINT `FK_MEDICO_RM_PERSONA`
	FOREIGN KEY (`ID_PERSONA`) REFERENCES `RM_PERSONA` (`ID_PERSONA`) 
;

ALTER TABLE `RM_MEDIDAS` 
 ADD CONSTRAINT `FK_RM_MEDIDAS_RM_CARETA`
	FOREIGN KEY (`ID_CARETA`) REFERENCES `RM_CARETA` (`ID_CARETA`) 
;

ALTER TABLE `RM_MEDIDAS` 
 ADD CONSTRAINT `FK_RM_MEDIDAS_RM_PACIENTE`
	FOREIGN KEY (`ID_PACIENTE`) REFERENCES `RM_PACIENTE` (`ID_PACIENTE`) 
;

ALTER TABLE `RM_MENU_OPCION` 
 ADD CONSTRAINT `FK_RM_MENU_OPCION_RM_MENU`
	FOREIGN KEY (`ID_MENU`) REFERENCES `RM_MENU` (`ID_MENU`) 
;

ALTER TABLE `RM_MENU_OPCION` 
 ADD CONSTRAINT `FK_RM_MENU_OPCION_RM_OPCION`
	FOREIGN KEY (`ID_OPCION`) REFERENCES `RM_OPCION` (`ID_OPCION`) 
;

ALTER TABLE `RM_MENU_OPCION` 
 ADD CONSTRAINT `FK_RM_MENU_OPCION_RM_SUBMENU`
	FOREIGN KEY (`ID_SUB_MENU`) REFERENCES `RM_MENU` (`ID_MENU`) 
;

ALTER TABLE `RM_PACIENTE` 
 ADD CONSTRAINT `FK_PACIENTE_HOSPITAL`
	FOREIGN KEY (`ID_HOSPITAL`) REFERENCES `RM_HOSPITAL` (`ID_HOSPITAL`) 
;

ALTER TABLE `RM_PACIENTE` 
 ADD CONSTRAINT `FK_PACIENTE_RM_PERSONA`
	FOREIGN KEY (`ID_PERSONA`) REFERENCES `RM_PERSONA` (`ID_PERSONA`) 
;

ALTER TABLE `RM_PACIENTE` 
 ADD CONSTRAINT `FK_RM_PACIENTE_RM_CARETA`
	FOREIGN KEY (`ID_CARETA`) REFERENCES `RM_CARETA` (`ID_CARETA`) 
;

ALTER TABLE `RM_PACIENTE` 
 ADD CONSTRAINT `FK_RM_PACIENTE_RM_ESTADOPACIENTE`
	FOREIGN KEY (`ID_ESTADOPACIENTE`) REFERENCES `RM_ESTADOPACIENTE` (`ID_ESTADOPACIENTE`) 
;

ALTER TABLE `RM_PACIENTE_MEDICO` 
 ADD CONSTRAINT `FK_RM_PACIENTE_MEDICO_RM_MEDICO`
	FOREIGN KEY (`ID_MEDICO`) REFERENCES `RM_MEDICO` (`ID_MEDICO`) 
;

ALTER TABLE `RM_PACIENTE_MEDICO` 
 ADD CONSTRAINT `FK_RM_PACIENTE_MEDICO_RM_PACIENTE`
	FOREIGN KEY (`ID_PACIENTE`) REFERENCES `RM_PACIENTE` (`ID_PACIENTE`) 
;

ALTER TABLE `RM_PERSONA` 
 ADD CONSTRAINT `FK_RM_PERSONA_RM_GENERO`
	FOREIGN KEY (`ID_GENERO`) REFERENCES `RM_GENERO` (`ID_GENERO`) 
;

ALTER TABLE `RM_ROL_MENU` 
 ADD CONSTRAINT `FK_RM_ROL_MENU_RM_MENU`
	FOREIGN KEY (`ID_MENU`) REFERENCES `RM_MENU` (`ID_MENU`) 
;

ALTER TABLE `RM_ROL_MENU` 
 ADD CONSTRAINT `FK_RM_ROL_MENU_RM_ROL`
	FOREIGN KEY (`ID_ROL`) REFERENCES `RM_ROL` (`ID_ROL`) 
;

ALTER TABLE `RM_SINTOMAS` 
 ADD CONSTRAINT `FK_RM_SINTOMAS_RM_PACIENTE`
	FOREIGN KEY (`ID_PACIENTE`) REFERENCES `RM_PACIENTE` (`ID_PACIENTE`) ON DELETE Restrict ON UPDATE Restrict
;

ALTER TABLE `RM_USUARIO` 
 ADD CONSTRAINT `FK_RM_USUARIO_RM_PERSONA`
	FOREIGN KEY (`ID_PERSONA`) REFERENCES `RM_PERSONA` (`ID_PERSONA`) 
;

ALTER TABLE `RM_USUARIO_ROL` 
 ADD CONSTRAINT `FK_RM_USUARIO_ROL_RM_ROL`
	FOREIGN KEY (`ID_ROL`) REFERENCES `RM_ROL` (`ID_ROL`) 
;

ALTER TABLE `RM_USUARIO_ROL` 
 ADD CONSTRAINT `FK_RM_USUARIO_ROL_RM_USUARIO`
	FOREIGN KEY (`ID_USUARIO`) REFERENCES `RM_USUARIO` (`ID_USUARIO`) 
;

SET FOREIGN_KEY_CHECKS=1
; 

COMMIT;
