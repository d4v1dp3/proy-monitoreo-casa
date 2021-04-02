/* ------------------------------------------------------------------------ */
/*  Script para carga de datos de ejemplo.   							    */
/*  Autor: Iliac Huerta Trujillo			      		    */
/*  Creado : 10-Sep-2020 04:15:34 p.m. 				    	    */
/*  DBMS   : MySql 							    */
/* ------------------------------------------------------------------------ */

/*Careta*/
INSERT INTO RM_CARETA (ID_CARETA,NO_SERIE,FECHA_MANUFACTURA) VALUES ("1","213747","2020-01-26");
INSERT INTO RM_CARETA (ID_CARETA,NO_SERIE,FECHA_MANUFACTURA) VALUES ("2","213748","2020-01-25");
INSERT INTO RM_CARETA (ID_CARETA,NO_SERIE,FECHA_MANUFACTURA) VALUES ("3","213749","2020-01-26");
INSERT INTO RM_CARETA (ID_CARETA,NO_SERIE,FECHA_MANUFACTURA) VALUES ("4","213750","2020-01-25");
INSERT INTO RM_CARETA (ID_CARETA,NO_SERIE,FECHA_MANUFACTURA) VALUES ("5","213751","2020-01-25");

/*Persona*/
INSERT INTO RM_PERSONA (ID_GENERO,NOMBRE,PRIMER_APELLIDO,SEGUNDO_APELLIDO,CURP,EDAD) 
VALUES (2,'JUAN','FLORES','CAMPOS','JUFC530902HSPRRN07',21);
INSERT INTO RM_PERSONA (ID_GENERO,NOMBRE,PRIMER_APELLIDO,SEGUNDO_APELLIDO,CURP,EDAD) 
VALUES (1,'ANA','VALENCIA','HERNANDEZ','ANVH280603MSPRRV09',30);

/*Hospital*/
INSERT INTO RM_HOSPITAL (NOMBRE, UBICACION_GEO, TEL_EMERGENCIAS, DIR_CALLE, NUMERO, COLONIA, MUNICIPIO, ESTADO)
		VALUES("IMSS (puerta 8)", "1224,23445", "5585327489", "Calz. Manuel Villalongín", 117, "Cuauhtémoc", "Mexico", "Ciudad de Mexico");

/*Paciente*/
INSERT INTO RM_PACIENTE (ID_PERSONA, DIR_CALLE, ID_HOSPITAL, DIR_NUMERO, DIR_INTERIOR, TEL_FIJO, TEL_CEL, ID_CARETA, ID_ESTADOPACIENTE) 
				VALUES (3, "ROMERO SANCHEZ", 1, 114, 5, 6654334555, 5563776900, 1, 1);
INSERT INTO RM_PACIENTE (ID_PERSONA, DIR_CALLE, ID_HOSPITAL, DIR_NUMERO, DIR_INTERIOR, TEL_FIJO, TEL_CEL, ID_CARETA, ID_ESTADOPACIENTE) 
				VALUES (4, "PLAZA JUAREZ", 1, 32, 2, 5564334555, 5543141673, 2, 1);

/*Antecedentes*/
INSERT INTO RM_ANTECEDENTES (ID_PACIENTE, DIABETES, CANCER, ASMA, VIH, HAS, EPOC, EMBARAZO, ARTRITIS, ENFAUTOINMUNE, FECHA)
			VALUES (1, TRUE, FALSE, TRUE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, '2021-01-20');

INSERT INTO RM_ANTECEDENTES (ID_PACIENTE, DIABETES, CANCER, ASMA, VIH, HAS, EPOC, EMBARAZO, ARTRITIS, ENFAUTOINMUNE, FECHA)
			VALUES (2, TRUE, FALSE, TRUE, FALSE, TRUE, FALSE, TRUE, FALSE, FALSE, '2021-01-20');

/*Usuario de Paciente*/
INSERT INTO RM_USUARIO (ID_USUARIO, CONTRASENIA, ACTIVO, ID_PERSONA,EMAIL)
VALUES ("paciente1", "paciente1", true, 3,'correo@gmail.com');
INSERT INTO RM_USUARIO (ID_USUARIO, CONTRASENIA, ACTIVO, ID_PERSONA,EMAIL)
VALUES ("paciente2", "paciente2", true, 4,'correo@gmail.com');

/*Médico*/
INSERT INTO RM_PERSONA (ID_GENERO,NOMBRE,PRIMER_APELLIDO,SEGUNDO_APELLIDO,CURP,EDAD) 
VALUES (2,'Alberto','Montero','Montero','ALMM490617HSPDSS05',40);
INSERT INTO RM_MEDICO (CEDULA_PROF,ID_PERSONA,CELULAR)
VALUES (123456,(SELECT ID_PERSONA FROM RM_PERSONA WHERE CURP = 'ALMM490617HSPDSS05'),55555555555);


/*relación medico hospital*/
INSERT INTO RM_HOSPITAL_MEDICO (ID_HOSPITAL, ID_MEDICO)
VALUES (1,(SELECT ID_MEDICO FROM RM_MEDICO WHERE CEDULA_PROF = 123456));

/*usuario médico de pruebas y rol de médico*/
INSERT INTO RM_USUARIO (ID_USUARIO, CONTRASENIA, ACTIVO, ID_PERSONA,EMAIL)
VALUES ('medico1','medico1',1,(SELECT ID_PERSONA FROM RM_PERSONA WHERE CURP = 'ALMM490617HSPDSS05'),'cuenta1notificaciones@gmail.com');

INSERT INTO RM_USUARIO_ROL (ID_USUARIO, ID_ROL)
VALUES ('medico1', 3);
INSERT INTO RM_USUARIO_ROL (ID_USUARIO, ID_ROL)
VALUES ('paciente1', 4);

/*relacion pacientes medico */
INSERT INTO RM_PACIENTE_MEDICO(ID_PACIENTE, ID_MEDICO, FECHA_INICIO)
VALUES ((SELECT ID_PACIENTE FROM RM_PACIENTE WHERE ID_PERSONA = 3),1,(SELECT SYSDATE()));

INSERT INTO RM_PACIENTE_MEDICO(ID_PACIENTE, ID_MEDICO, FECHA_INICIO)
VALUES ((SELECT ID_PACIENTE FROM RM_PACIENTE WHERE ID_PERSONA = 4),1,(SELECT SYSDATE()));

/*Careta Hospital*/
INSERT INTO RM_CARETA_HOSPITAL (FECHA_ASIGNACION,ID_CARETA,ID_HOSPITAL) VALUES ("2021-01-20","1","1");
INSERT INTO RM_CARETA_HOSPITAL (FECHA_ASIGNACION,ID_CARETA,ID_HOSPITAL) VALUES ("2021-01-20","2","1");
INSERT INTO RM_CARETA_HOSPITAL (FECHA_ASIGNACION,ID_CARETA,ID_HOSPITAL) VALUES ("2021-01-20","3","1");
INSERT INTO RM_CARETA_HOSPITAL (FECHA_ASIGNACION,ID_CARETA,ID_HOSPITAL) VALUES ("2021-01-20","4","1");


INSERT INTO RM_MEDIDAS (ID_PACIENTE,ID_CARETA,ID_MEDICION,FECHA_MEDICION,ALERTA,
SATURACION_OXIGENO,TEMPERATURA,CAPNOGRAFIA,FREC_CARDIACA,FREC_RESPIRATORIA,PRE_ART_SISTOLICA,PRE_ART_DIASTOLICA) 
VALUES (1, 1, 1, now(), false, 98, 36, 35, 100,80, 120,70);

INSERT INTO RM_MEDIDAS (ID_PACIENTE,ID_CARETA,ID_MEDICION,FECHA_MEDICION,ALERTA,
SATURACION_OXIGENO,TEMPERATURA,CAPNOGRAFIA,FREC_CARDIACA,FREC_RESPIRATORIA,PRE_ART_SISTOLICA,PRE_ART_DIASTOLICA) 
VALUES (1, 1, 2, now(), false, 98, 36,39, 72, 18, 120,70);

INSERT INTO RM_MEDIDAS (ID_PACIENTE,ID_CARETA,ID_MEDICION,FECHA_MEDICION,ALERTA,
SATURACION_OXIGENO,TEMPERATURA,CAPNOGRAFIA,FREC_CARDIACA,FREC_RESPIRATORIA,PRE_ART_SISTOLICA,PRE_ART_DIASTOLICA) 
VALUES (1, 1, 3, now(), false, 96, 37, 50, 72, 18, 120,70);

INSERT INTO RM_MEDIDAS (ID_PACIENTE,ID_CARETA,ID_MEDICION,FECHA_MEDICION,ALERTA,
SATURACION_OXIGENO,TEMPERATURA,CAPNOGRAFIA ,FREC_CARDIACA,FREC_RESPIRATORIA,PRE_ART_SISTOLICA,PRE_ART_DIASTOLICA) 
VALUES (1, 1, 4, now(), false, 94, 36, 38, 72, 18, 120,70);

INSERT INTO RM_MEDIDAS (ID_PACIENTE,ID_CARETA,ID_MEDICION,FECHA_MEDICION,ALERTA,
SATURACION_OXIGENO,TEMPERATURA,CAPNOGRAFIA ,FREC_CARDIACA,FREC_RESPIRATORIA,PRE_ART_SISTOLICA,PRE_ART_DIASTOLICA) 
VALUES (1, 1, 5, now(), false, 98, 34, 36, 72, 18, 120,70);




commit;
