--Ejecutar primero
--DROP DATABASE CalendarioLaboral WITH (FORCE);
--Ejecutar segundo
CREATE DATABASE CalendarioLaboral;

--Para las siguientes instrucciones, se debe cambiar la conexión

--Crear la tabla TIPO
CREATE TABLE Tipo(
	Id SERIAL PRIMARY KEY,
	Tipo VARCHAR(100) NOT NULL
	);

/* Crear indice para TIPO
	ordenado por FECHA */
CREATE UNIQUE INDEX ixTipo
	ON Tipo(Tipo);

--Crear la tabla CALENDARIO
CREATE TABLE Calendario(
	Id SERIAL PRIMARY KEY,
	Fecha DATE NOT NULL,
	idTipo INT NOT NULL,
    CONSTRAINT fkCalendario_Tipo FOREIGN KEY (idTipo) REFERENCES Tipo(Id),
    Descripcion VARCHAR(100) NULL
	);

/* Crear indice para CALENDARIO
	ordenado por FECHA */
CREATE UNIQUE INDEX ixCalendario
	ON Calendario(Fecha);
