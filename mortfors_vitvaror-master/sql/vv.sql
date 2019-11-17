\c jimmy
\encoding utf8
\set ON_ERROR_STOP on
DROP DATABASE store;
CREATE DATABASE store WITH ENCODING 'UTF8';
\c store
CREATE SCHEMA appliances;

CREATE TABLE appliances.product (
	id serial primary key,
	manufacturer text,
	model text,
	sale_price int,
	type text
);

CREATE TABLE appliances.supplier (
	id serial primary key,
	name text,
	phone_number text,
	email text
);	

CREATE TABLE appliances.product_unit (
	id serial primary key,
	product_id int,
	supply_price int,
	supplier_id int,
	stock int,
	
	foreign key (product_id) references appliances.product(id),
	foreign key (supplier_id) references appliances.supplier(id)
);

CREATE TABLE appliances.customer (
	customer_id bigint primary key,
	name text,
	email text
);

CREATE TABLE appliances.customer_purchase (
	product_id int,
	customer_id bigint,
	quantity int,
	price real,
	
	primary key (product_id, customer_id),
	foreign key (product_id) references appliances.product(id),
	foreign key (customer_id) references appliances.customer(customer_id)
);

INSERT INTO appliances.customer (customer_id, name, email) VALUES
	(8706301111, 'Linus Forsberg', 'forsberglinus@gmail.com'),
	(8208021111, 'Jimmy Akesson', 'jimmyakesson@sd.se'),
	(9505051111, 'Maximilian Falkenström', 'maxfalk@gmail.com'), 
	(5501031111, 'Leif Johansson', 'leffe@live.com'), 
	(4603081111, 'Göran Persson', 'sosse@socialdemokraterna.se'), 
	(9407021111, 'Justin Biever', 'kakaka@lol.com'), 
	(3501011111, 'Bert Karlsson', 'bertan@skara.com'),
	(4304329232, 'Donald Trump', 'DonaldJTrump@wh.gov'),
	(4403180923, 'Hillary Clinton', 'HillaryClinton@theclintonfoundation.org');
	
INSERT INTO appliances.product (manufacturer, model, sale_price, type) VALUES 
	('Electrolux', 'x2500', 12500, 'Kylskåp'),
	('Bosch', 'k1000', 7000, 'Ugn'),
	('Samsung', 's10edge', 950, 'Mikrovågsugn'),
	('LG', 'freezing5000', 9995, 'Frys'),
	('Husqvarna', 'wash100', 7500, 'Diskmaskin'),
	('AKT', 'f200', 2000, 'Köksfläkt'),
	('LG', 'XX9000', 15000, 'Kylskåp'),
	('LoL', 'HOT8000', 4000, 'Ugn'),
	('AquaClean', '4500cs', 8000, 'Diskmaskin'),
	('Quality Stones Ltd.', 'Marble Tile Exquisite', 99999999, 'Marmorplatta'),
	('Samsung', 'FA30943049 OLED', 9495, 'TV'),
	('Saudi Luxury Jets', 'Make Jets Great Again', 41074107, 'Flygplan');
	
INSERT INTO appliances.supplier(name, phone_number, email) VALUES
	('Karls Vitvaror', '0708554433', 'karlsvitvaror@mail.se'),
	('Electrolux', '020223344', 'matsolsson@electrolux.com'),
	('Elcentralen', '020886611', 'gunnelsvensson@elcentralen.se'),
	('Bengts kyl&frys', '0798336677', 'bengtskof@gmail.se'),
	('Handelsboden', '040335588', 'handelsboden@mail.se'),
	('Kyl&Frys AB', '067397461', 'kylfrys@foretagsmail.se'),
	('Marble Distributors', '0763645234', 'inquiry@marbledistributors.com'),
	('Acme Distribution Corp', '0754236423', 'service@acme.com'),
	('Saudi Luxury Jets Dealership', '0784938596', 'jets@saudiluxury.gold');
	
INSERT INTO appliances.product_unit (product_id, supply_price, supplier_id, stock) VALUES
	(10, 69999999, 7, 52),
	(4, 5995, 3, 14),
	(2,2049,3,5),
	(1,5200,2,10),
	(4,5800,6,12),
	(4,4500,2,5),
	(9,700,5,20),
	(12, 10741074, 9, 14);

INSERT INTO appliances.customer_purchase (product_id, customer_id, quantity, price) VALUES
	(10, 4304329232, -24, 99999999),
	(8, 9505051111, -1, 4000),
	(4, 8706301111, -2, 9995),
	(1, 3501011111, -1, 2999.99),
	(4, 8208021111, -1, 1099.00),
	(4, 9407021111, -2, 599.99),
	(3, 9505051111, -1, 2599.95),
	(12, 4304329232, -3, 41074107);
	
	
	

