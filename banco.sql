
#Me aseguro de eliminar la base, en caso de existir
DROP DATABASE IF EXISTS banco;

#Elimino posibles usuarios anteriores
drop user 'admin'@'localhost';
drop user 'empleado'@'%';
drop user 'atm'@'%';

#Creo la base de datos
CREATE DATABASE banco;

#Selecciono la base de datos para la cual voy a realizar modificaciones
USE banco;

#Creación de tablas

CREATE TABLE ciudad (
 	cod_postal INTEGER(4) UNSIGNED NOT NULL,
 	nombre VARCHAR(45) NOT NULL,

 	CONSTRAINT pk_ciudad
	PRIMARY KEY(cod_postal)

)ENGINE=InnoDB;

CREATE TABLE sucursal (

	nro_suc INTEGER (3) UNSIGNED NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(45) NOT NULL,
	direccion VARCHAR(45) NOT NULL,
	telefono VARCHAR(45) NOT NULL,
	horario VARCHAR(45) NOT NULL,
	cod_postal INTEGER(4) UNSIGNED NOT NULL,

	CONSTRAINT fk_sucursal_codpostal
	FOREIGN KEY (cod_postal) REFERENCES Ciudad(cod_postal)
	ON DELETE RESTRICT ON UPDATE CASCADE,
	
	CONSTRAINT pk_sucursal
	PRIMARY KEY (nro_suc)

)ENGINE=InnoDB;

CREATE TABLE empleado(
	legajo INTEGER (4) UNSIGNED NOT NULL AUTO_INCREMENT,
	apellido VARCHAR(45) NOT NULL,
	nombre VARCHAR(45) NOT NULL,
	tipo_doc VARCHAR(20) NOT NULL,
	nro_doc INTEGER(8) UNSIGNED NOT NULL,
	direccion VARCHAR(45) NOT NULL,
	telefono VARCHAR(45) NOT NULL,
	cargo VARCHAR(45) NOT NULL,
	password VARCHAR(32) NOT NULL,
	nro_suc INTEGER(3) UNSIGNED NOT NULL,
	
	CONSTRAINT fk_nrosucursal_empleado
	FOREIGN KEY (nro_suc) REFERENCES Sucursal(nro_suc)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT pk_empleado
	PRIMARY KEY (legajo)

)ENGINE=InnoDB;

CREATE TABLE cliente(
	nro_cliente INT (5) UNSIGNED NOT NULL AUTO_INCREMENT,
	apellido VARCHAR(45) NOT NULL,
	nombre VARCHAR(45) NOT NULL,
	tipo_doc VARCHAR(20) NOT NULL,
	nro_doc  INTEGER(8) UNSIGNED NOT NULL,
	direccion VARCHAR(45) NOT NULL,
	telefono VARCHAR(45) NOT NULL,
	fecha_nac DATE 	NOT NULL,
	
	CONSTRAINT pk_cliente
	PRIMARY KEY (nro_cliente)

)ENGINE=InnoDB;

CREATE TABLE plazo_fijo(
	nro_plazo INTEGER (8) UNSIGNED NOT NULL AUTO_INCREMENT,
	capital DECIMAL(16,2) UNSIGNED NOT NULL,
	tasa_interes DECIMAL(4,2)UNSIGNED NOT NULL,
	interes  DECIMAL(16,2)UNSIGNED NOT NULL,
	nro_suc INTEGER(3) UNSIGNED NOT NULL,
	fecha_inicio DATE NOT NULL,
	fecha_fin DATE NOT NULL,
	
	CONSTRAINT fk_nrosucursal_plazo_fijo
	FOREIGN KEY (nro_suc) REFERENCES sucursal(nro_suc)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT pk_nroplazo
	PRIMARY KEY (nro_plazo)
	
)ENGINE=InnoDB;

CREATE TABLE tasa_plazo_fijo(
	periodo INTEGER(3) UNSIGNED NOT NULL,
	monto_inf DECIMAL(16,2) UNSIGNED NOT NULL,
	monto_sup DECIMAL(16,2) UNSIGNED NOT NULL,
	tasa  DECIMAL(4,2) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_periodo_tasa_plazo_fijo
	PRIMARY KEY (periodo,monto_inf,monto_sup)

)ENGINE=InnoDB;

CREATE TABLE plazo_cliente(
	nro_plazo INTEGER(8) UNSIGNED NOT NULL,
	nro_cliente INTEGER(5) UNSIGNED NOT NULL,
	
	CONSTRAINT fk_nroPlazo_plazo_cliente
	FOREIGN KEY (nro_plazo) REFERENCES plazo_fijo(nro_plazo)
	ON DELETE RESTRICT ON UPDATE CASCADE,
	
	CONSTRAINT fk_nroCliente_plazo_cliente
	FOREIGN KEY (nro_cliente) REFERENCES cliente(nro_cliente)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT pk_nroPlazo_plazo_cliente
	PRIMARY KEY (nro_plazo,nro_cliente)
	
)ENGINE=InnoDB;

CREATE TABLE prestamo(
	nro_prestamo INTEGER (8) UNSIGNED NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,
	cant_meses INTEGER(2) UNSIGNED NOT NULL,
	monto DECIMAL(10,2) UNSIGNED NOT NULL,
	tasa_interes DECIMAL(4,2) UNSIGNED NOT NULL,
	interes DECIMAL(9,2)UNSIGNED NOT NULL,
	valor_cuota DECIMAL(9,2) UNSIGNED NOT NULL,
	legajo INTEGER(4) UNSIGNED NOT NULL,
	nro_cliente INTEGER(5) UNSIGNED NOT NULL,
	
	CONSTRAINT fk_legajo_prestamo
	FOREIGN KEY (legajo) REFERENCES empleado(legajo)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT fk_nroCliente_prestamo
	FOREIGN KEY (nro_cliente) REFERENCES cliente(nro_cliente)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT pk_nroPrestamo_prestamo
	PRIMARY KEY (nro_prestamo)

)ENGINE=InnoDB;


CREATE TABLE pago(
	nro_prestamo INTEGER(8) UNSIGNED NOT NULL,
	nro_pago INTEGER(2) UNSIGNED NOT NULL,
	fecha_venc DATE NOT NULL,
	fecha_pago DATE,
	
	CONSTRAINT fk_nroPrestamo_pago
	FOREIGN KEY (nro_prestamo) REFERENCES prestamo(nro_prestamo)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT pk_nroPrestamo_pago
	PRIMARY KEY (nro_prestamo,nro_pago)

)ENGINE=InnoDB;

CREATE TABLE tasa_prestamo(
	periodo INTEGER(3) UNSIGNED NOT NULL,
	monto_inf DECIMAL(10,2) UNSIGNED NOT NULL,
	monto_sup DECIMAL(10,2) UNSIGNED NOT NULL,
	tasa  DECIMAL(4,2) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_periodo_tasa_prestamo
	PRIMARY KEY (periodo,monto_inf,monto_sup)

)ENGINE=InnoDB;

CREATE TABLE caja_ahorro(
	nro_ca  INTEGER(8) UNSIGNED NOT NULL AUTO_INCREMENT,
	CBU BIGINT(18) UNSIGNED NOT NULL,
	saldo DECIMAL(16,2)UNSIGNED NOT NULL,
	
	CONSTRAINT pk_nroCa_caja_ahorro
	PRIMARY KEY (nro_ca)

)ENGINE=InnoDB;

CREATE TABLE cliente_ca(
	nro_cliente INTEGER(5) UNSIGNED NOT NULL,
	nro_ca INTEGER(8) UNSIGNED NOT NULL,
	

	
	CONSTRAINT fk_nroCliente_clienteCA
	FOREIGN KEY (nro_cliente) REFERENCES cliente(nro_cliente)
	ON DELETE RESTRICT ON UPDATE CASCADE,
	
	CONSTRAINT fk_nroCA_clienteCA
	FOREIGN KEY (nro_ca) REFERENCES caja_ahorro(nro_ca)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT pk_nroCliente_clienteCA
	PRIMARY KEY (nro_cliente,nro_ca)

)ENGINE=InnoDB;

CREATE TABLE tarjeta(
	nro_tarjeta BIGINT(16) UNSIGNED NOT NULL AUTO_INCREMENT,
	PIN VARCHAR(32) NOT NULL,
	CVT VARCHAR(32) NOT NULL,
	fecha_venc DATE NOT NULL,
	nro_cliente INTEGER(5) UNSIGNED NOT NULL,
	nro_ca INTEGER(8) UNSIGNED NOT NULL,
		
	CONSTRAINT fk_nroCliente_tarjeta
	FOREIGN KEY (nro_cliente,nro_ca) REFERENCES cliente_ca(nro_cliente,nro_ca)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT pk_nroTarjeta_tarjeta
	PRIMARY KEY (nro_tarjeta)

)ENGINE=InnoDB;

CREATE TABLE caja(
	cod_caja INTEGER(5) UNSIGNED NOT NULL AUTO_INCREMENT,
	
	CONSTRAINT pk_codCAja_caja
	PRIMARY KEY (cod_caja)

)ENGINE=InnoDB;

CREATE TABLE ventanilla(
	cod_caja INTEGER(5) UNSIGNED NOT NULL,
	nro_suc INTEGER(3) UNSIGNED NOT NULL,
	
	CONSTRAINT fk_codCaja_ventanilla
	FOREIGN KEY (cod_caja) REFERENCES caja(cod_caja)
	ON DELETE RESTRICT ON UPDATE CASCADE,
	
	CONSTRAINT fk_nrosucursal_ventanilla
	FOREIGN KEY (nro_suc) REFERENCES sucursal(nro_suc)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT pk_codCAja_caja
	PRIMARY KEY (cod_caja)

)ENGINE=InnoDB;

CREATE TABLE atm(
	cod_caja INTEGER(5) UNSIGNED NOT NULL AUTO_INCREMENT,
	cod_postal INTEGER(4) UNSIGNED NOT NULL,
	direccion VARCHAR(45) NOT NULL,
	
	CONSTRAINT fk_codPostal_ATM
	FOREIGN KEY (cod_postal) REFERENCES ciudad(cod_postal)
	ON DELETE RESTRICT ON UPDATE CASCADE,
	
	CONSTRAINT fk_codCaja_ATM
	FOREIGN KEY (cod_caja) REFERENCES caja(cod_caja)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT pk_codCAja_ATM
	PRIMARY KEY (cod_caja)

)ENGINE=InnoDB;

CREATE TABLE transaccion(
	nro_trans INTEGER(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,
	hora TIME NOT NULL ,
	monto DECIMAL(16,2) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_nroTrans_transaccion
	PRIMARY KEY (nro_trans)

)ENGINE=InnoDB;

CREATE TABLE debito(
	nro_trans INTEGER(10) UNSIGNED NOT NULL,
	descripcion TEXT,
	nro_cliente INTEGER(5) UNSIGNED NOT NULL,
	nro_ca INTEGER(8) UNSIGNED NOT NULL,
	
	CONSTRAINT fk_nroCliente_debito
	FOREIGN KEY (nro_cliente,nro_ca) REFERENCES cliente_ca(nro_cliente,nro_ca)
	ON DELETE RESTRICT ON UPDATE CASCADE,
	
	CONSTRAINT fk_nroTrans_debito
	FOREIGN KEY (nro_trans) REFERENCES transaccion(nro_trans)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT pk_nroTrans_debito
	PRIMARY KEY (nro_trans)

)ENGINE=InnoDB;

CREATE TABLE transaccion_por_caja(
	nro_trans INTEGER(10) UNSIGNED NOT NULL,
	cod_caja INTEGER(5) UNSIGNED NOT NULL,
	
	CONSTRAINT fk_codCaja_transPorCaja
	FOREIGN KEY (cod_caja) REFERENCES caja(cod_caja)
	ON DELETE RESTRICT ON UPDATE CASCADE,
	
	CONSTRAINT fk_nroTrans_transPorCaja
	FOREIGN KEY (nro_trans) REFERENCES transaccion(nro_trans)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT pk_nroTrans_transPorCaja
	PRIMARY KEY (nro_trans)

)ENGINE=InnoDB;

CREATE TABLE deposito(
	nro_trans INTEGER(10) UNSIGNED NOT NULL,
	nro_ca INTEGER(8) UNSIGNED NOT NULL,
	
	CONSTRAINT fk_nroCA_deposito
	FOREIGN KEY (nro_ca) REFERENCES caja_ahorro(nro_ca)
	ON DELETE RESTRICT ON UPDATE CASCADE,
	
	CONSTRAINT fk_nroTrans_deposito
	FOREIGN KEY (nro_trans) REFERENCES transaccion_por_caja(nro_trans)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT pk_nroTrans_deposito
	PRIMARY KEY (nro_trans)
	
)ENGINE=InnoDB;

CREATE TABLE extraccion(
	nro_trans INTEGER(10) UNSIGNED NOT NULL,
	nro_cliente INTEGER(5) UNSIGNED NOT NULL,
	nro_ca INTEGER(8) UNSIGNED NOT NULL,
	
	CONSTRAINT pk_nroTrans_extraccion
	PRIMARY KEY (nro_trans),
	
	CONSTRAINT fk_nroCA_extraccion
	FOREIGN KEY (nro_ca,nro_cliente) REFERENCES cliente_ca(nro_ca,nro_cliente),
	
	CONSTRAINT fk_nroTrans_extraccion
	FOREIGN KEY (nro_trans) REFERENCES transaccion_por_caja(nro_trans)	

)ENGINE=InnoDB;

CREATE TABLE transferencia(
	nro_trans INTEGER(10) UNSIGNED NOT NULL,
	nro_cliente INTEGER(5) UNSIGNED NOT NULL,
	origen INTEGER(8) UNSIGNED NOT NULL,
	destino INT UNSIGNED NOT NULL AUTO_INCREMENT,

	
	CONSTRAINT fk_destino_transferencia
	FOREIGN KEY (destino) REFERENCES caja_ahorro(nro_ca)
	ON DELETE RESTRICT ON UPDATE CASCADE,
	
	CONSTRAINT fk_nroCliente_transferencia
	FOREIGN KEY (nro_cliente,origen) REFERENCES cliente_ca(nro_cliente,nro_ca)
	ON DELETE RESTRICT ON UPDATE CASCADE,
	
	CONSTRAINT fk_nroTrans_transferencia
	FOREIGN KEY (nro_trans) REFERENCES transaccion_por_caja(nro_trans)
	ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT pk_nroTrans_transferencia
	PRIMARY KEY (nro_trans)

)ENGINE=InnoDB;

#-------------------------------------------------------------------------------------------------------
#VISTAS

CREATE VIEW trans_cajas_ahorro AS
	#Debito
	SELECT trans.nro_trans,trans.fecha,trans.hora,trans.monto,'debito' AS tipo,caj.nro_ca,saldo,clien.nro_cliente AS 'nro_cliente',
			clien.apellido,clien.nombre,clien.tipo_doc,clien.nro_doc AS 'nro_doc','NULL' AS destino,'NULL' AS cod_caja
	FROM transaccion trans JOIN debito deb ON trans.nro_trans=deb.nro_trans
	JOIN  cliente clien ON deb.nro_cliente=clien.nro_cliente
	JOIN caja_ahorro caj ON deb.nro_ca=caj.nro_ca
	
	UNION ALL
	
	#Extraccion
	SELECT trans.nro_trans,trans.fecha,trans.hora,trans.monto,'extraccion' AS tipo,caj.nro_ca,saldo,clien.nro_cliente AS 'nro_cliente',
			clien.apellido,clien.nombre,clien.tipo_doc,clien.nro_doc AS 'nro_doc','NULL' AS destino,trans_caja.cod_caja
	FROM transaccion trans JOIN extraccion extrac ON trans.nro_trans=extrac.nro_trans
	JOIN transaccion_por_caja trans_caja ON trans_caja.nro_trans=extrac.nro_trans
	JOIN cliente clien ON extrac.nro_cliente=clien.nro_cliente
	JOIN caja_ahorro caj ON extrac.nro_ca=caj.nro_ca

	UNION ALL
	
	#Transferencia
	SELECT trans.nro_trans,trans.fecha,trans.hora,trans.monto,'transferencia' AS tipo,caj.nro_ca,saldo,clien.nro_cliente AS 'nro_cliente',
			clien.apellido,clien.nombre,clien.tipo_doc,clien.nro_doc AS 'nro_doc',transf.destino AS destino,trans_caja.cod_caja
	FROM transaccion trans JOIN transferencia transf ON trans.nro_trans=transf.nro_trans
	JOIN transaccion_por_caja trans_caja ON trans_caja.nro_trans=transf.nro_trans
	JOIN cliente clien ON transf.nro_cliente=clien.nro_cliente
	JOIN caja_ahorro caj ON transf.origen=caj.nro_ca
	
	UNION ALL	
	
	#Deposito
	SELECT trans.nro_trans,trans.fecha,trans.hora,trans.monto,'deposito' AS tipo,caj.nro_ca,saldo,'NULL' AS nro_cliente,
			'NULL' AS apellido,'NULL' AS nombre,'NULL' AS tipo_doc,'NULL' AS nro_doc,'NULL' AS destino,trans_caja.cod_caja
	FROM transaccion trans JOIN deposito dep ON trans.nro_trans=dep.nro_trans
	JOIN transaccion_por_caja trans_caja ON trans_caja.nro_trans=dep.nro_trans
	JOIN caja_ahorro caj ON dep.nro_ca=caj.nro_ca;

#
#-----------------------------------------------------------------------------------------------------------------------------------------
#STORED PROCEDURES
#
#

DELIMITER !
CREATE PROCEDURE transferir(IN montotrans DECIMAL(16,2), IN origen INT(8), IN destino INT(8), IN atm INT(5), IN titular SMALLINT(5))
	BEGIN
		DECLARE Saldo_Actual_Origen DECIMAL(16,2);
		DECLARE Saldo_Final_Origen DECIMAL(16,2);
		DECLARE id BIGINT(10);
		BEGIN
			SELECT 'SQLEXCEPTION! - Transferencia abortada' AS resultado;
			ROLLBACK;
		END;
	
		START TRANSACTION;
		IF EXISTS (SELECT * FROM Caja_Ahorro WHERE nro_ca=destino) 
		THEN
			IF EXISTS (SELECT * FROM Caja_Ahorro WHERE nro_ca=origen) 
			THEN
				IF EXISTS (SELECT * FROM Cliente_CA WHERE nro_ca=origen AND nro_cliente=titular) 
				THEN
					SELECT saldo INTO Saldo_Actual_Origen
					FROM Caja_Ahorro WHERE nro_ca = origen FOR UPDATE;
					IF Saldo_Actual_Origen >= montotrans 
					THEN
						UPDATE Caja_Ahorro SET saldo = saldo - montotrans WHERE nro_ca=origen;
						UPDATE Caja_Ahorro SET saldo = saldo + montotrans WHERE nro_ca=destino;

						INSERT INTO Transaccion(fecha, hora, monto) VALUES(curdate(), curtime(), montotrans);
						SELECT last_insert_id() INTO id;
						INSERT INTO Transaccion_por_caja(nro_trans, cod_caja) VALUES(id, atm);
						INSERT INTO Transferencia(nro_trans, nro_cliente, origen, destino) VALUES(id, titular, origen, destino);

						INSERT INTO Transaccion(fecha, hora, monto) VALUES(curdate(), curtime(), montotrans);
						SELECT last_insert_id() INTO id;
						INSERT INTO Transaccion_por_caja(nro_trans, cod_caja) VALUES(id, atm);
						INSERT INTO Deposito(nro_trans, nro_ca) VALUES(id, destino);
					
						SELECT saldo INTO Saldo_Final_Origen FROM caja_ahorro WHERE nro_ca = origen;
						SELECT 'La transferencia se ha realizado con exito' AS resultado,Saldo_Final_Origen AS saldo;
					ELSE
						SELECT 'Error: Saldo insuficiente' AS resultado,Saldo_Actual_Origen AS saldo ;
					END IF;
				ELSE
					SELECT 'Error: El cliente no es titular de la cuenta' AS resultado,'-' AS saldo;
				END IF;
			ELSE
				SELECT 'Error: La cuenta origen no existe.' AS resultado,'-' AS saldo;
			END IF;
		ELSE
			SELECT 'Error: La cuenta destino no existe' AS resultado,'-' AS saldo;
		END IF;
		COMMIT;
	END!
DELIMITER ;




DELIMITER !
CREATE PROCEDURE extraer(IN atm INT(5), IN cliente SMALLINT(5),IN cajaAhorro INT(8), IN montoExtr DECIMAL(16,2))
	BEGIN
		DECLARE Saldo_Inicial DECIMAL(16,2);
		DECLARE Saldo_Final DECIMAL(16,2);
		DECLARE id BIGINT(10);
    
		BEGIN
			SELECT 'SQLEXCEPTION!, Extraccion abortada' AS resultado;
			ROLLBACK;
		END;
  
		START TRANSACTION;
		IF EXISTS (SELECT * FROM Tarjeta WHERE nro_cliente = cliente AND nro_ca = cajaAhorro) 
		THEN
			SELECT saldo INTO Saldo_Inicial FROM Caja_Ahorro WHERE nro_ca = cajaAhorro FOR UPDATE;
			IF Saldo_Inicial >= montoExtr 
			THEN
				UPDATE Caja_Ahorro SET saldo = saldo - montoExtr WHERE nro_ca = cajaAhorro;

				SELECT last_insert_id() INTO id;
				INSERT INTO Transaccion(fecha, hora, monto) VALUES(curdate(), curtime(), montoExtr);
				INSERT INTO Extraccion(nro_trans, nro_cliente, nro_ca) VALUES(id, cliente, cajaAhorro);
				INSERT INTO Transaccion_por_caja(nro_trans, cod_caja) VALUES(id, atm);
				
				SELECT saldo INTO Saldo_Final FROM caja_ahorro WHERE nro_ca =cajaAhorro;
				SELECT 'La extraccion se ha realizado con exito.' AS resultado,Saldo_Final AS saldo;
			ELSE
				SELECT 'Error: Saldo insuficiente para la extraccion.' AS resultado,'-' AS saldo;
			END IF;
		ELSE
			SELECT 'Error: La cuenta no existe.' AS resultado,'-' AS saldo;
		END IF;
		COMMIT;
	END!
DELIMITER ;
#---------------------------------------------------------------------------------------------------------------------------------------------------
#TRIGGERS

delimiter !

CREATE TRIGGER cargar_pagos_a_prestamo
AFTER INSERT ON prestamo
FOR EACH ROW
	BEGIN
		DECLARE cont int;
	
		SET cont = 0;
		WHILE cont<NEW.cant_meses do
			INSERT INTO pago (nro_pago,nro_prestamo,fecha_venc,fecha_pago)VALUES
							 (cont+1,NEW.nro_prestamo, ADDDATE(curdate(), INTERVAL cont+1 MONTH),NULL); 
	
			SET cont = cont+1;
		END WHILE;
	END; !
delimiter ;
 
#-------------------------------------------------------------------------------------------------------
#CREACION DE USUARIOS Y OTORGAMIENTO DE PRIVILEGIOS

	#Creo los usuarios
	
	CREATE USER 'admin'@'localhost'IDENTIFIED BY 'admin';
	CREATE USER 'empleado'@'%' IDENTIFIED BY 'empleado';
	CREATE USER 'atm'@'%' IDENTIFIED BY 'atm';
	
	#contraseñas de usuarios
	
	SET PASSWORD FOR 'admin'@'localhost'=PASSWORD('admin');
	SET PASSWORD FOR 'empleado'@'%'=PASSWORD('empleado');
	SET PASSWORD FOR 'atm'@'%'=PASSWORD('atm');
	
	#privilegios de admin
	
	GRANT ALL PRIVILEGES ON banco.* TO 'admin'@'localhost' WITH GRANT OPTION;

	#privilegios de atm
	GRANT SELECT,UPDATE ON banco.tarjeta TO 'atm'@'%';
	GRANT SELECT ON banco.trans_cajas_ahorro TO 'atm'@'%';
	GRANT EXECUTE ON PROCEDURE banco.extraer TO 'atm'@'%';
	GRANT EXECUTE ON PROCEDURE banco.transferir TO 'atm'@'%';

	#privilegios de empleado
	
	GRANT SELECT ON banco.empleado TO 'empleado'@'%';
	GRANT SELECT ON banco.sucursal TO 'empleado'@'%';
	GRANT SELECT ON banco.tasa_plazo_fijo TO 'empleado'@'%';
	GRANT SELECT ON banco.tasa_prestamo TO 'empleado'@'%';
	
	GRANT SELECT,INSERT ON banco.prestamo TO 'empleado'@'%';
	GRANT SELECT,INSERT ON banco.plazo_fijo TO 'empleado'@'%';
	GRANT SELECT,INSERT ON banco.plazo_cliente TO 'empleado'@'%';
	GRANT SELECT,INSERT ON banco.caja_ahorro TO 'empleado'@'%';
	GRANT SELECT,INSERT ON banco.tarjeta TO 'empleado'@'%';
	
	GRANT SELECT,INSERT,UPDATE ON banco.cliente_ca TO 'empleado'@'%';
	GRANT SELECT,INSERT,UPDATE ON banco.cliente TO 'empleado'@'%';
	GRANT SELECT,INSERT,UPDATE ON banco.pago TO 'empleado'@'%';
	
	
