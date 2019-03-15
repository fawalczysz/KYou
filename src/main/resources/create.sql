CREATE TABLE Products (
    id      INTEGER PRIMARY KEY auto_increment,
    barCode VARCHAR2(15) 
);

CREATE TABLE Users (
    id        INTEGER PRIMARY KEY auto_increment,
    firstname VARCHAR2(30),
    lastname  VARCHAR2(30),
    email     VARCHAR2(30)  UNIQUE
                      NOT NULL
);


CREATE TABLE Baskets (
    id           INTEGER PRIMARY KEY auto_increment,
    userId       INTEGER NOT NULL,
    basketNumber INTEGER NOT NULL
);


CREATE TABLE Nutriments (
    id           INTEGER PRIMARY KEY auto_increment,
    productId    INTEGER NOT NULL,
    name         VARCHAR2(30)  NOT NULL,
    component    BOOLEAN NOT NULL,
    valueFor100g DOUBLE  NOT NULL
);


CREATE TABLE BasketProducts (
    id            INTEGER PRIMARY KEY auto_increment,
    basketId      INTEGER ,
    productId     INTEGER ,
    productNumber INTEGER,
    UNIQUE (
        basketId,
        productId
    )
);
