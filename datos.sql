#datos.sql
#Consultar el tema de las fechas
USE banco;

DELETE FROM transferencia;
DELETE FROM extraccion;
DELETE FROM deposito;
DELETE FROM transaccion_por_caja;
DELETE FROM debito;
DELETE FROM transaccion;
DELETE FROM atm;
DELETE FROM ventanilla;
DELETE FROM caja;
DELETE FROM tarjeta;
DELETE FROM cliente_ca;
DELETE FROM caja_ahorro;
DELETE FROM tasa_prestamo;
DELETE FROM pago;
DELETE FROM prestamo;
DELETE FROM plazo_cliente;
DELETE FROM tasa_plazo_fijo;
DELETE FROM plazo_fijo;
DELETE FROM cliente;
DELETE FROM empleado;
DELETE FROM sucursal;
DELETE FROM ciudad;

#Ciudades
INSERT	INTO	ciudad	VALUES	(8109, 'Punta Alta');
INSERT	INTO	ciudad	VALUES	(8000, 'Bahia Blanca');
INSERT  INTO 	ciudad	VALUES	(5454, 'Suarez');
INSERT  INTO 	ciudad	VALUES	(9272, 'Medanos');

#Sucursales
INSERT	INTO	sucursal	VALUES	(1, 'Galicia', 'Irigoyen 348', '02932438360', '10 a 18', 8109);
INSERT	INTO	sucursal	VALUES	(2, 'Nacion', 'Colon 577', '02932414243', '10 a 18', 8000);
INSERT	INTO	sucursal	VALUES	(3, 'Provincia', 'Chiclana 21', '02914142434', '10 a 18', 5454);
INSERT	INTO	sucursal	VALUES	(4, 'Santander', 'Colon 81', '02914383601', '10 a 18', 9272);

#Empleados
INSERT	INTO	empleado	VALUES	(1, 'Volpe', 'Leandro', 'LE', 00000001, 'Colon 80', '2915333812', 'Vice Gerente', '12345678', 1);
INSERT	INTO	empleado	VALUES	(2, 'Sanchez', 'Juan', 'DNI', 33825128, 'Zapiola 480', '2915324812', 'Ventanilla', 'artebh88', 2);
INSERT	INTO	empleado	VALUES	(3, 'Pedersen', 'Gabriel', 'DNI', 33563287, 'Ing. White 8310', '2915333812', 'Ventanilla', '12345678', 3);
INSERT	INTO	empleado	VALUES	(4, 'Vercelli', 'Franco', 'DNI', 37235140, 'Castelli 3800', '291030345', 'Secretario', 'nanananana', 4);

#Pass de tabla empleado
UPDATE empleado set password=md5(password);

#Clientes
INSERT	INTO	cliente		VALUES	(1, 'Arce', 'Lucio', 'DNI', 39014284, 'Santa Fe 890', '291528359', '1987-05-04');
INSERT	INTO	cliente		VALUES	(2, 'Meneses', 'Christian', 'DNI', 35603912, 'Por la CocaCola', '291438359', '1990-03-10');
INSERT	INTO	cliente		VALUES	(3, 'Larrasolo', 'Francisco', 'DNI', 36327311, 'Dr Aguad 832', '291543159', '1988-02-09');
INSERT	INTO	cliente		VALUES	(4, 'Orge',	'Agustin', 'DNI', 34200900, 'BNPB Casa 37', '2932528359', '1999-10-10');

#Plazos fijos
INSERT	INTO	plazo_fijo	VALUES	(1,	20000.00, 40.00, 25.00, 1, '2018-03-01', '2019-03-01');
INSERT	INTO	plazo_fijo	VALUES	(2, 100000.00, 40.00, 25.00, 2, '2018-05-01', '2018-09-01');
INSERT	INTO	plazo_fijo	VALUES	(3, 4000.00, 40.00, 2.00, 3, '2018-05-21', '2018-06-21');
INSERT	INTO	plazo_fijo	VALUES	(4, 7500.00, 50.00, 25.00, 1, '2018-07-15', '2019-07-15');

#Tasas de plazo fijo
INSERT	INTO	tasa_plazo_fijo	VALUES	(001, 1000.00, 1000000.00, 02.40);
INSERT	INTO	tasa_plazo_fijo	VALUES	(002, 1000.00, 1000000.00, 05.55);
INSERT	INTO	tasa_plazo_fijo	VALUES	(003, 1000.00, 1000000.00, 12.00);

#Plazos de clientes
INSERT	INTO	plazo_Cliente	VALUES	(1,4);
INSERT	INTO	plazo_Cliente	VALUES	(2,3);
INSERT	INTO	plazo_Cliente	VALUES	(3,2);
INSERT	INTO	plazo_Cliente	VALUES	(4,1);

#Prestamos
INSERT	INTO	prestamo		VALUES	(00011111,'2018-02-05', 06, 5000.00, 30.00, 12.0 ,1500.00, 1, 1);
INSERT	INTO	prestamo		VALUES	(00022222,'2018-02-05', 12, 50000.00, 30.00, 13.0 ,1500.00, 2, 2);

#Pagos
INSERT	INTO	pago			VALUES	(00011111, 02, '2018-05-08', '2018-04-05');
INSERT	INTO	pago			VALUES	(00011111, 03, '2018-05-08', '2018-05-05');
INSERT	INTO	pago			VALUES	(00022222, 01, '2019-02-05', '2018-03-05');

#Tasas prestamos
INSERT	INTO	tasa_prestamo	VALUES	(090,1000.00,1000000.00,15.00);
INSERT	INTO	tasa_prestamo	VALUES	(030,1000.00,1000000.00,4.00);

#Cajas de ahorro
INSERT	INTO	caja_ahorro VALUES (00000001, 000000000000000011, 27000.00);
INSERT	INTO	caja_ahorro VALUES (00000002, 000000000000000012, 2000.00);
INSERT	INTO	caja_ahorro VALUES (00000003, 000000000000000013, 50000.00);
INSERT	INTO	caja_ahorro VALUES (00000004, 000000000000000014, 60.00);

#Cajas de ahorro de clientes
INSERT 	INTO	cliente_ca	VALUES	(1,00000001);
INSERT	INTO	cliente_ca	VALUES	(2,00000002);
INSERT	INTO	cliente_ca	VALUES	(3,00000003);
INSERT	INTO	cliente_ca	VALUES	(4,00000004);

#Tarjetas
INSERT	INTO	tarjeta		VALUES	(7434, 'PINAAAAAAAAAAAAAAAAAAAAAAAAAAAAA', 'CBTAAAAAAAAAAAAAAAAAAAAAAAAAAAAA','2018-10-13 ',1, 00000001);
INSERT	INTO	tarjeta		VALUES	(3546, 'PINBBBBBBBBBBBBBBBBBBBBBBBBBBBBB', 'CBTBBBBBBBBBBBBBBBBBBBBBBBBBBBBB','2019-10-11',2, 00000002);
INSERT	INTO	tarjeta		VALUES	(3773, 'PINCCCCCCCCCCCCCCCCCCCCCCCCCCCCC', 'CBTCCCCCCCCCCCCCCCCCCCCCCCCCCCCC','2020-01-10 ',3, 00000003);
INSERT	INTO	tarjeta		VALUES	(2743, 'PINDDDDDDDDDDDDDDDDDDDDDDDDDDDDD', 'CBTDDDDDDDDDDDDDDDDDDDDDDDDDDDDD','2019-04-03',4, 00000004);

#Encripto datos de tarjetas
UPDATE tarjeta set PIN=md5(PIN);
UPDATE tarjeta set CVT=md5(CVT);

#Cajas
INSERT	INTO	caja 		VALUES	(12345);
INSERT	INTO	caja 		VALUES	(23456);
INSERT	INTO	caja 		VALUES	(55567);
INSERT	INTO	caja 		VALUES	(88877);

#Ventanilla
INSERT	INTO	ventanilla	VALUES	(12345, 3);
INSERT	INTO	ventanilla	VALUES	(23456, 4);

#ATMs
INSERT	INTO	atm			VALUES	(55567, 8109, 'Alem 348');
INSERT	INTO	atm			VALUES	(88877, 8000, 'Siria 577');

#Transacciones
INSERT	INTO	transaccion	VALUES	(0000000100, '2018-09-17', '16:00:00', 500.00);
INSERT	INTO	transaccion	VALUES	(0000000007, '2018-09-12','16:00:02', 6000.00);
INSERT	INTO	transaccion	VALUES	(0000000101, '2018-09-17', '17:20:00', 3000.00);
INSERT	INTO	transaccion	VALUES	(0000000500, '2018-09-17', '16:30:20', 500.00);

#Debito
INSERT	INTO	debito		VALUES	(0000000100, 'Deposito con motivo de algo', 1, 00000001);

#Transaccion por caja
INSERT	INTO	transaccion_por_caja	VALUES	(0000000007,12345);

#Deposito
INSERT	INTO	deposito	VALUES	(0000000007, 00000001);

#Extraccion
INSERT	INTO	extraccion	VALUES	(0000000007, 3,00000003);

#Transferencia
INSERT	INTO	transferencia	VALUES	(0000000007,4,00000004,00000002);