/* ------------------------------------------------------------------------ */
/*  Base de datos para el sistema de Monitore Remoto para pacientes COVID   */
/*  Autor: Iliac Huerta Trujillo			      		    */
/*  Creado : 10-Sep-2020 04:15:34 p.m. 				    	    */
/*  DBMS   : MySql 							    */
/* ------------------------------------------------------------------------ */

/*--Borrar tablas*/
TRUNCATE TABLE RM_MENU_OPCION;
TRUNCATE TABLE RM_ROL_MENU;
TRUNCATE TABLE RM_USUARIO_ROL;
TRUNCATE TABLE RM_BITACORA;
TRUNCATE TABLE RM_HOSPITAL_MEDICO;
DELETE FROM RM_MEDICO;
DELETE FROM RM_USUARIO;
DELETE FROM RM_PERSONA;
DELETE FROM RM_GENERO;

DELETE FROM RM_OPCION;
DELETE FROM RM_MENU;
DELETE FROM RM_ROL;

/*--- Definiciones de Roles*/
INSERT INTO RM_ROL (ID_ROL, DESCRIPCION) VALUES (1,'ADMINISTRADOR');
INSERT INTO RM_ROL (ID_ROL, DESCRIPCION) VALUES (2,'APOYO');
INSERT INTO RM_ROL (ID_ROL, DESCRIPCION) VALUES (3,'MEDICO');
INSERT INTO RM_ROL (ID_ROL, DESCRIPCION) VALUES (4,'PACIENTE');
INSERT INTO RM_ROL (ID_ROL, DESCRIPCION) VALUES (5,'ADMON_HOSPITAL');

/* -- Definición de estado de paciente-- */
INSERT INTO RM_ESTADOPACIENTE (ID_ESTADOPACIENTE, DESCRIPCION) VALUES(1, 'Estable');
INSERT INTO RM_ESTADOPACIENTE (ID_ESTADOPACIENTE, DESCRIPCION) VALUES(2, 'Grave');

/*---- Definiciones de menus*/
INSERT INTO RM_MENU (ID_MENU, DESCRIPCION, RUTA_ICONO, POSICION) VALUES (1,'General','fa fa-cog',999);
INSERT INTO RM_MENU (ID_MENU, DESCRIPCION, RUTA_ICONO, POSICION) VALUES (2,'Administrador','fas fa-user-ninja',1);
INSERT INTO RM_MENU (ID_MENU, DESCRIPCION, RUTA_ICONO, POSICION) VALUES (3,'Médico','fas fa-user-md',2);
INSERT INTO RM_MENU (ID_MENU, DESCRIPCION, RUTA_ICONO, POSICION) VALUES (4,'Paciente','fas fa-bed',2);

/*-- Definición de Opciones*/
Insert into RM_OPCION (ID_OPCION, DESCRIPCION, RUTA_ICONO, ACCION) Values (1, 'Salir', 'ui-icon-close', '#{sesionMB.cerrarSesion}');
Insert into RM_OPCION (ID_OPCION, DESCRIPCION, RUTA_ICONO, ACCION) Values (2, 'Gestión usuarios', 'fas fa-id-card', '/faces/facelets/admon/gestionUsuarios.xhtml');
Insert into RM_OPCION (ID_OPCION, DESCRIPCION, RUTA_ICONO, ACCION) Values (3, 'Gestión Hospital', 'fas fa-hospital', '/faces/facelets/admon/gestionHospital.xhtml');
Insert into RM_OPCION (ID_OPCION, DESCRIPCION, RUTA_ICONO, ACCION) Values (4, 'Pacientes', 'fas fa-procedures', '/faces/facelets/medico/listaPacientesMedico.xhtml');
Insert into RM_OPCION (ID_OPCION, DESCRIPCION, RUTA_ICONO, ACCION) Values (5, 'Lista Medicos', 'fas fa-user-md', '/faces/facelets/admon/listaMedicos.xhtml');
Insert into RM_OPCION (ID_OPCION, DESCRIPCION, RUTA_ICONO, ACCION) Values (6, 'Mis Datos', 'fas fa-clipboard', '/faces/facelets/paciente/inicioPaciente.xhtml');

select * from RM_OPCION;

/*--Asociación de Rol con Menu*/
INSERT INTO RM_ROL_MENU (ID_MENU, ID_ROL) VALUES (1,1); /*--Todos los roles tienen el menú general*/
INSERT INTO RM_ROL_MENU (ID_MENU, ID_ROL) VALUES (2,1);
INSERT INTO RM_ROL_MENU (ID_MENU, ID_ROL) VALUES (1,2);
INSERT INTO RM_ROL_MENU (ID_MENU, ID_ROL) VALUES (1,3);
INSERT INTO RM_ROL_MENU (ID_MENU, ID_ROL) VALUES (1,4);
INSERT INTO RM_ROL_MENU (ID_MENU, ID_ROL) VALUES (1,5);
INSERT INTO RM_ROL_MENU (ID_MENU, ID_ROL) VALUES (3,3);
INSERT INTO RM_ROL_MENU (ID_MENU, ID_ROL) VALUES (4,4);

/*Definición de opciones para cada menú*/
INSERT INTO RM_MENU_OPCION (ID_MENU_OPCION,ID_MENU,ID_OPCION,ID_SUB_MENU,POSICION) VALUES (1,1,1,null,999);
INSERT INTO RM_MENU_OPCION (ID_MENU_OPCION,ID_MENU,ID_OPCION,ID_SUB_MENU,POSICION) VALUES (2,2,2,null,1);
INSERT INTO RM_MENU_OPCION (ID_MENU_OPCION,ID_MENU,ID_OPCION,ID_SUB_MENU,POSICION) VALUES (3,2,3,null,2);
INSERT INTO RM_MENU_OPCION (ID_MENU_OPCION,ID_MENU,ID_OPCION,ID_SUB_MENU,POSICION) VALUES (4,3,4,null,1);
INSERT INTO RM_MENU_OPCION (ID_MENU_OPCION,ID_MENU,ID_OPCION,ID_SUB_MENU,POSICION) VALUES (5,3,5,null,2);
INSERT INTO RM_MENU_OPCION (ID_MENU_OPCION,ID_MENU,ID_OPCION,ID_SUB_MENU,POSICION) VALUES (6,4,6,null,2);

/*--- Definición catálogo de géneros*/
INSERT INTO RM_GENERO (ID_GENERO,DESCRIPCION) VALUES(1,'FEMENINO');
INSERT INTO RM_GENERO (ID_GENERO,DESCRIPCION) VALUES(2,'MASCULINO');
INSERT INTO RM_GENERO (ID_GENERO,DESCRIPCION) VALUES(3,'N/E');


/*-- Persona inicial*/
INSERT INTO RM_PERSONA (ID_PERSONA,ID_GENERO,NOMBRE,PRIMER_APELLIDO,SEGUNDO_APELLIDO,CURP) VALUES (1,3,'JHON','DOE','DOE','JDD80202020');
INSERT INTO RM_PERSONA (ID_PERSONA,ID_GENERO,NOMBRE,PRIMER_APELLIDO,SEGUNDO_APELLIDO,CURP) VALUES (2,2,'ILIAC','HUERTA','TRUJILLO','HUTI070707');


/*-- Usuario */
INSERT INTO RM_USUARIO (ID_USUARIO,CONTRASENIA,ACTIVO,ID_PERSONA) VALUES ('johndoe','johndoe',TRUE,1);
INSERT INTO RM_USUARIO (ID_USUARIO,CONTRASENIA,ACTIVO,ID_PERSONA) VALUES ('ihuerta','ihuerta123',TRUE,2);


/*---usuario rol*/

INSERT INTO RM_USUARIO_ROL (ID_USUARIO,ID_ROL) VALUES ('johndoe',1);
INSERT INTO RM_USUARIO_ROL (ID_USUARIO,ID_ROL) VALUES ('ihuerta',1);
INSERT INTO RM_USUARIO_ROL (ID_USUARIO,ID_ROL) VALUES ('ihuerta',2);
INSERT INTO RM_USUARIO_ROL (ID_USUARIO,ID_ROL) VALUES ('ihuerta',5);

/*Careta*/
INSERT INTO RM_CARETA (FECHA_MANUFACTURA) VALUES ("2020-01-26");
INSERT INTO RM_CARETA (FECHA_MANUFACTURA) VALUES ("2020-01-25");

/*Persona*/
INSERT INTO RM_PERSONA (ID_GENERO,NOMBRE,PRIMER_APELLIDO,SEGUNDO_APELLIDO,CURP) 
VALUES (2,'JUAN','FLORES','CAMPOS','JFC80202020');
INSERT INTO RM_PERSONA (ID_GENERO,NOMBRE,PRIMER_APELLIDO,SEGUNDO_APELLIDO,CURP) 
VALUES (1,'ANA','VALENCIA','HERNANDEZ','AVHI070707');

/*Hospital*/
INSERT INTO RM_HOSPITAL (NOMBRE, UBICACION_GEO, TEL_EMERGENCIAS, DIR_CALLE, NUMERO, COLONIA, MUNICIPIO, ESTADO)
		VALUES("IMSS (puerta 8)", "1224,23445", "5585327489", "Calz. Manuel Villalongín", 117, "Cuauhtémoc", "Mexico", "Ciudad de Mexico");

/*Paciente*/
INSERT INTO RM_PACIENTE (ID_PERSONA, DIR_CALLE, ID_HOSPITAL, DIR_NUMERO, DIR_INTERIOR, TEL_FIJO, TEL_CEL, ID_CARETA, ID_ESTADOPACIENTE) 
				VALUES (3, "ROMERO SANCHEZ", 1, 114, 5, 6654334555, 5563776900, 1, 1);
INSERT INTO RM_PACIENTE (ID_PERSONA, DIR_CALLE, ID_HOSPITAL, DIR_NUMERO, DIR_INTERIOR, TEL_FIJO, TEL_CEL, ID_CARETA, ID_ESTADOPACIENTE) 
				VALUES (4, "PLAZA JUAREZ", 1, 32, 2, 5564334555, 5543141673, 2, 1);

select * from RM_PACIENTE;

/*Usuario de Paciente*/
INSERT INTO RM_USUARIO (ID_USUARIO, CONTRASENIA, ACTIVO, ID_PERSONA)
VALUES ("paciente1", "paciente1", true, 3);
INSERT INTO RM_USUARIO (ID_USUARIO, CONTRASENIA, ACTIVO, ID_PERSONA)
VALUES ("paciente2", "paciente2", true, 4);

/*Médico*/
INSERT INTO RM_PERSONA (ID_GENERO,NOMBRE,PRIMER_APELLIDO,SEGUNDO_APELLIDO,CURP) 
VALUES (2,'Alberto','Montero','Montero','MOMA80202021');
INSERT INTO RM_MEDICO (CEDULA_PROF,ID_PERSONA,EMAIL,CELULAR)
VALUES (123456,(SELECT ID_PERSONA FROM RM_PERSONA WHERE CURP = 'MOMA80202021'), 'albertomm@prueba.test',55555555555);

select * from RM_PERSONA;

/*relación medico hospital*/
INSERT INTO RM_HOSPITAL_MEDICO (ID_HOSPITAL, ID_MEDICO)
VALUES (1,(SELECT ID_MEDICO FROM RM_MEDICO WHERE CEDULA_PROF = 123456));

/*usuario médico de pruebas y rol de médico*/
INSERT INTO RM_USUARIO (ID_USUARIO, CONTRASENIA, ACTIVO, ID_PERSONA)
VALUES ('medico1','medico1',1,(SELECT ID_PERSONA FROM RM_PERSONA WHERE CURP = 'MOMA80202021'));

INSERT INTO RM_USUARIO_ROL (ID_USUARIO, ID_ROL)
VALUES ('medico1', 3);

/*relacion pacientes medico */
INSERT INTO RM_PACIENTE_MEDICO(ID_PACIENTE, ID_MEDICO, FECHA_INICIO)
VALUES ((SELECT ID_PACIENTE FROM RM_PACIENTE WHERE ID_PERSONA = 3),1,(SELECT SYSDATE()));

INSERT INTO RM_PACIENTE_MEDICO(ID_PACIENTE, ID_MEDICO, FECHA_INICIO)
VALUES ((SELECT ID_PACIENTE FROM RM_PACIENTE WHERE ID_PERSONA = 4),1,(SELECT SYSDATE()));

select * from RM_PACIENTE;

truncate rm_medidas;
truncate rm_valores_referencia;

#Campos presion arterial
alter table rm_valores_referencia
add PRE_ART_SISTOLICA_MIN int not null
default 80,
add PRE_ART_SISTOLICA_MAX int not null
default 120,
add PRE_ART_DIASTOLICA_MIN int not null
default 60,
add PRE_ART_DIASTOLICA_MAX int not null
default 80;

desc rm_valores_referencia;

#Valores de referencia
insert into rm_valores_referencia (SAT_OXIGENO_MIN,SAT_OXIGENO_MAX,TEMPERATURA_MIN,TEMPERATURA_MAX,CAPNOGRAFIA_MIN,CAPNOGRAFIA_MAX,
FREC_CARDIACA_MIN,FREC_CARDIACA_MAX,FREC_RESPIRATORIA_MIN,FREC_RESPIRATORIA_MAX,ID_VALREF,PRE_ART_SISTOLICA_MIN,PRE_ART_SISTOLICA_MAX,PRE_ART_DIASTOLICA_MIN,PRE_ART_DIASTOLICA_MAX)
values(95.0,100.0,34.2,40.0,123,123,60,100,12,18,1,90,120,60,80);

#Medidas paciente1
insert into rm_medidas (FECHA_MEDICION,SATURACION_OXIGENO,TEMPERATURA,CAPNOGRAFIA,FREC_CARDIACA,FREC_RESPIRATORIA,ID_PACIENTE,ID_CARETA,ALERTA,PRE_ART_SISTOLICA,PRE_ART_DIASTOLICA)
values(now(),92.4,34.5,40,75,10,1,(select ID_CARETA from rm_paciente where ID_PACIENTE=1),1,120,70);
select SLEEP(1);
insert into rm_medidas (FECHA_MEDICION,SATURACION_OXIGENO,TEMPERATURA,CAPNOGRAFIA,FREC_CARDIACA,FREC_RESPIRATORIA,ID_PACIENTE,ID_CARETA,ALERTA,PRE_ART_SISTOLICA,PRE_ART_DIASTOLICA)
values(now(),95.4,36.5,40,120,12,1,(select ID_CARETA from rm_paciente where ID_PACIENTE=1),1,140,70);
select SLEEP(1);
insert into rm_medidas (FECHA_MEDICION,SATURACION_OXIGENO,TEMPERATURA,CAPNOGRAFIA,FREC_CARDIACA,FREC_RESPIRATORIA,ID_PACIENTE,ID_CARETA,ALERTA,PRE_ART_SISTOLICA,PRE_ART_DIASTOLICA)
values(now(),90,40.5,40,60,19,1,(select ID_CARETA from rm_paciente where ID_PACIENTE=1),1,180,70);

#Medidas paciente2
insert into rm_medidas (FECHA_MEDICION,SATURACION_OXIGENO,TEMPERATURA,CAPNOGRAFIA,FREC_CARDIACA,FREC_RESPIRATORIA,ID_PACIENTE,ID_CARETA,ALERTA,PRE_ART_SISTOLICA,PRE_ART_DIASTOLICA)
values(now(),90.4,41.5,40,55,13,2,(select ID_CARETA from rm_paciente where ID_PACIENTE=2),1,180,80);
select SLEEP(1);
insert into rm_medidas (FECHA_MEDICION,SATURACION_OXIGENO,TEMPERATURA,CAPNOGRAFIA,FREC_CARDIACA,FREC_RESPIRATORIA,ID_PACIENTE,ID_CARETA,ALERTA,PRE_ART_SISTOLICA,PRE_ART_DIASTOLICA)
values(now(),95.4,36.5,40,60,10,2,(select ID_CARETA from rm_paciente where ID_PACIENTE=2),1,150,60);
select SLEEP(1);
insert into rm_medidas (FECHA_MEDICION,SATURACION_OXIGENO,TEMPERATURA,CAPNOGRAFIA,FREC_CARDIACA,FREC_RESPIRATORIA,ID_PACIENTE,ID_CARETA,ALERTA,PRE_ART_SISTOLICA,PRE_ART_DIASTOLICA)
values(now(),97.4,37.5,40,100,17,2,(select ID_CARETA from rm_paciente where ID_PACIENTE=2),1,115,75);

commit;
