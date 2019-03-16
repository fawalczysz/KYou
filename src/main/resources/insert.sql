
INSERT INTO Products (id,barCode) values(0,'1234');
INSERT INTO Products (id,barCode) values(1,'12345');
INSERT INTO Products (id,barCode) values(2,'123456');
INSERT INTO Products (id,barCode) values(3,'1234567');
INSERT INTO Products (id,barCode) values(4,'12345678');

/* BON*/
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(0,0,'energy_100g',0,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(1,0,'satured-fat_100g',0,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(2,0,'sugars_100g',0,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(3,0,'salt_100g',0,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(4,0,'fiber_100g',1,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(5,0,'proteins_100g',1,0);

/*MOYEN*/
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(6,1,'energy_100g',0,1000);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(7,1,'satured-fat_100g',0,100);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(8,1,'sugars_100g',0,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(9,1,'salt_100g',0,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(10,1,'fiber_100g',1,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(11,1,'proteins_100g',1,5);

/*mauvais*/
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(12,2,'energy_100g',0,1500);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(13,2,'satured-fat_100g',0,7);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(14,2,'sugars_100g',0,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(15,2,'salt_100g',0,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(16,2,'fiber_100g',1,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(17,2,'proteins_100g',1,0);

/*tres mauvais*/
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(18,3,'energy_100g',0,1000);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(19,3,'satured-fat_100g',0,100);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(20,3,'sugars_100g',0,100);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(21,3,'salt_100g',0,100);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(22,3,'fiber_100g',1,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(23,3,'proteins_100g',1,0);

/*tres bon*/
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(24,4,'energy_100g',0,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(25,4,'satured-fat_100g',0,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(26,4,'sugars_100g',0,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(27,4,'salt_100g',0,0);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(28,4,'fiber_100g',1,1);
INSERT INTO Nutriments (id,productId,name,component,valueFor100g) VALUES
(29,4,'proteins_100g',1,0);


INSERT INTO Users(id,firstname,lastname,email) VALUES (0,'toto','toto','toto.toto@toto.com');
INSERT INTO Users(id,firstname,lastname,email) VALUES (1,'titi','titi','titi.titi@titi.com');

INSERT INTO Baskets (id,userId,basketNumber) VALUES (0,0,0);
INSERT INTO Baskets (id,userId,basketNumber) VALUES (1,0,1);
INSERT INTO Baskets (id,userId,basketNumber) VALUES (2,0,2);

INSERT INTO Baskets (id,userId,basketNumber) VALUES (3,1,0);
INSERT INTO Baskets (id,userId,basketNumber) VALUES (4,1,1);
INSERT INTO Baskets (id,userId,basketNumber) VALUES (5,1,2);

INSERT INTO BasketProducts (id,basketId,productId,productNumber) VALUES(0,1,0,1);
INSERT INTO BasketProducts (id,basketId,productId,productNumber) VALUES(1,2,1,3);

INSERT INTO BasketProducts (id,basketId,productId,productNumber) VALUES(3,3,0,1);
INSERT INTO BasketProducts (id,basketId,productId,productNumber) VALUES(4,3,1,3);
