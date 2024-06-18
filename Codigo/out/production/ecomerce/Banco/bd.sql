-- Tabela "Client"
CREATE TABLE Client (
                        id_client INTEGER PRIMARY KEY AUTOINCREMENT,
                        name NVARCHAR(160) NOT NULL,
                        phone VARCHAR(20) NOT NULL,
                        cpf VARCHAR(11) NOT NULL,
                        email NVARCHAR(160) NOT NULL,
                        password NVARCHAR(160) NOT NULL,
                        date_birth DATETIME,
                        address NVARCHAR(160) NOT NULL
);


-- Tabela "Login"
CREATE TABLE Login
(
    id_client INT NOT NULL,
    name NVARCHAR(160) NOT NULL,
    email NVARCHAR(160) NOT NULL,
    CONSTRAINT FK_Client FOREIGN KEY (id_client) REFERENCES Client(id_client)
);

CREATE TABLE Category (
                          id_Category INTEGER PRIMARY KEY AUTOINCREMENT,
                          name NVARCHAR(160) NOT NULL
);


CREATE TABLE Stock (
                       id_Stock INTEGER PRIMARY KEY AUTOINCREMENT,
                       id_Product INTEGER NOT NULL,
                       amount INTEGER NOT NULL,
                       FOREIGN KEY (id_Product) REFERENCES Product(id_Product)
);


CREATE TABLE Product (
                         id_Product INTEGER PRIMARY KEY AUTOINCREMENT,
                         name TEXT NOT NULL,
                         price REAL NOT NULL,
                         id_Category INTEGER NOT NULL,
                         quantity INTEGER NOT NULL,
                         FOREIGN KEY (id_Category) REFERENCES Category(id_Category)
);



CREATE TABLE Itens_Product
(
    id_Itens_Product INTEGER NOT NULL,
    id_Product INTEGER NOT NULL,
    amount INT NOT NULL,
    CONSTRAINT PK_Itens_Product PRIMARY KEY (id_Itens_Product),
    CONSTRAINT FK_Product FOREIGN KEY (id_Product) REFERENCES Product(id_Product)
);


CREATE TABLE Order_itens
(
    id_Order INTEGER NOT NULL,
    id_Client INTEGER NOT NULL,
    id_Itens_Product INTEGER NOT NULL,
    payment NVARCHAR(160) NOT NULL,
    order_date DATETIME NOT NULL,
    total_value DOUBLE,
    CONSTRAINT PK_Order_itens PRIMARY KEY (id_Order),
    CONSTRAINT FK_Client FOREIGN KEY (id_Client) REFERENCES Client(id_Client),
	CONSTRAINT FK_Itens_Product FOREIGN KEY (id_Itens_Product) REFERENCES Itens_Product(id_Itens_Product)
);



SELECT
    P.name AS ProductName,
    C.name AS CategoryName,
    IP.amount AS SoldAmount,
    O.payment AS PaymentMethod,
    O.order_date AS OrderDate
FROM
    Product AS P
INNER JOIN
    Category AS C ON P.id_Category = C.id_Category
INNER JOIN
    Itens_Product AS IP ON P.id_Product = IP.id_Product
INNER JOIN
    Order_itens AS O ON IP.id_Itens_Product = O.id_Itens_Product;




