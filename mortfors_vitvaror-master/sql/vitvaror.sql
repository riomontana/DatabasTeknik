\c jimmy
\set ON_ERROR_STOP on
DROP DATABASE store;
CREATE DATABASE store WITH ENCODING "UTF-8";
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
	website text
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
	address text,
	email text
);

CREATE TABLE appliances.customer_purchase (
	id serial primary key,
	product_id int,
	customer_id bigint,
	quantity int,
	price real,
	date Timestamp,

	foreign key (product_id) references appliances.product(id),
	foreign key (customer_id) references appliances.customer(customer_id)
);

INSERT INTO appliances.customer (customer_id, name, address, email) VALUES
	(8706301111, 'Linus Forsberg', 'ivögatan 8a, 21422 Malmö', 'forsberglinus@gmail.com'),
	(8208021111, 'Jimmy Akesson', 'svenska stigen 3, 22222 Svennebo', 'jimmyakesson@sd.se'),
	(9505051111, 'Maximilian Falkenström', 'rönnblomsgatan 10, 22211, Helsingborg', 'maxfalk@gmail.com'), 
	(5501031111, 'Leif Johansson', 'vasagatan 100, 44231, Götlaborg', 'leffe@live.com'), 
	(4603081111, 'Goran Persson', 'sossegatan 130, 19922, Sosseholm', 'sosse@socialdemokraterna.se'), 
	(9407021111, 'Justin Biever', 'lugna gatan 20, 43212, Vancouver', 'kakaka@lol.com'), 
	(3501011111, 'Bert Karlsson', 'dansbandsgatan 34, 53055, Skara sommarland','bertan@skara.com'),
	(4304329232, 'Donald Trump', 'White House, 20593, Washington DC','DonaldJTrump@wh.gov'),
	(4403180923, 'Hillary Clinton', 'New Street 450, 32945, New Hampshire', 'HillaryClinton@theclintonfoundation.org');
	
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
	('Quality Microwave Ltd.', 'Wave500', 799, 'Mikrovågsugn'),
	('Samsung', 'FA30943049 Oven', 9495, 'Ugn'),
	('SaudiAC', 'AC10000XC', 5000, 'Luftkondition'),
	('ChinaWare', 'ABC100', 2995, 'Spis'),
	('Tralala', 'ktd23434', 3456, 'Kylskåp'),
	('GoodStuff', 'khkh3333', 2000, 'Frys'),
	('Tecnoven', 'tv532', 7000, 'Ugn'),
	('High Quality Washers', '2000X', 3500, 'Diskmaskin'),
	('MELK', 'hka1111', 4000, 'Tvättmaskin'),
	('ABC', 'G5432', 5000, 'Torktumlare'),
	('AAADDD', '4500D', 6000, 'Torkskåp');
	
INSERT INTO appliances.supplier(name, phone_number, website) VALUES
	('Karls Vitvaror', '0708554433', 'karlsvitvaror.se'),
	('Electrolux', '020223344', 'electrolux.com'),
	('Elcentralen', '020886611', 'elcentralen.se'),
	('Bengts kyl&frys', '0798336677', 'bengtskof.se'),
	('Handelsboden', '040335588', 'handelsboden.se'),
	('Kyl&Frys AB', '067397461', 'kylfrys.se'),
	('KitchenWare Distributors', '0763645234', 'kitchenwaredistributors.com'),
	('Acme Distribution Corp', '0754236423', 'acme.com'),
	('Saudi Air Condition Dealership', '0784938596', 'saudiaircondition.sa'),
	('Fiffel & Båg AB', '0303333333', 'lassekroner@f&b.se'),
	('Billig o Bra', '101000222', 'billig@billig.com'),
	('Best Buy', '084444566', 'bjarne@betbuy.com'),
	('NetOnNet', '112345678', 'netonnet@siba.se'),
	('Elgiganten', '087765331', 'leif@gigant.se'),
	('Svenska Vitvaror', '0102234545', 'vitvaror@vitvaror.se');
	
INSERT INTO appliances.product_unit (product_id, supply_price, supplier_id, stock) VALUES
	(10, 4000, 7, 520),
	(4, 5995, 3, 140),
	(2,2049,3,500),
	(1,5200,2,100),
	(4,5800,6,120),
	(4,4500,2,500),
	(9,700,5,200),
	(12, 3500, 9, 140),
	(3, 4000, 8, 1500),
	(5, 2000, 1, 200),
	(6, 1500, 3, 2000),
	(7, 466, 4, 1200),
	(8, 4700, 5, 1500),
	(11, 300, 6, 654),
	(12, 333, 7,4578 ),
	(13, 1234, 8, 555),
	(14, 1111, 9, 332),
	(15, 999, 10, 356),
	(16, 1155, 10, 765),
	(17, 996, 11, 444),
	(18, 532, 12, 678),
	(19, 2467, 13, 7775),
	(20, 666, 14, 345),
	(10, 443, 15, 666);


INSERT INTO appliances.customer_purchase (product_id, customer_id, quantity, price, date) VALUES
	(10, 4304329232, -24, 99999999, '2017-06-01 12:56:10.677'),
	(8, 9505051111, -1, 4000, '2017-06-01 12:56:10.677'),
	(4, 8706301111, -2, 9995, '2017-06-01 12:56:10.677'),
	(1, 3501011111, -1, 2999.99, '2017-06-01 12:56:10.677'),
	(4, 8208021111, -1, 1099.00, '2017-06-01 12:56:10.677'),
	(4, 9407021111, -2, 599.99, '2017-06-01 12:56:10.677'),
	(3, 9505051111, -1, 2599.95, '2017-06-01 12:56:10.677'),
	(12, 4304329232, -3, 41074107, '2017-06-01 12:56:10.677');
	
	
	

