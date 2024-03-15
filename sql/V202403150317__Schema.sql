Use bean_bank_coin_schema;

CREATE TABLE `BeanType` (
    `BeanTypeID` int NOT NULL AUTO_INCREMENT,
    `BeanName` varchar(120) NOT NULL,
    `BeanSymbol` varchar(3) NOT NULL,
    `ValueInRands` int NOT NULL,
    PRIMARY KEY (`BeanTypeID`),
    CONSTRAINT UNIQUE (`BeanName`),
    CONSTRAINT UNIQUE (`BeanSymbol`)
);

CREATE TABLE `TransactionsType` (
    `TransactionTypeID` int NOT NULL,
    `TransactionTypeName` varchar(20) NOT NULL,
    PRIMARY KEY (`TransactionTypeID`)
);

CREATE TABLE `User` (
    `UserID` int NOT NULL AUTO_INCREMENT,
    `UserName` varchar(120) NOT NULL,
    PRIMARY KEY (`UserID`),
    CONSTRAINT UNIQUE (`UserName`)
);

CREATE TABLE `Account` (
    `AccountID` int NOT NULL AUTO_INCREMENT,
    `UserID` int NOT NULL,
    `BeanTypeID` int NOT NULL,
    `BalanceAmount` decimal NOT NULL,
    `DateCreated` datetime NOT NULL,
    `IsClosed` boolean NOT NULL,
    PRIMARY KEY (`AccountID`),
    CONSTRAINT FOREIGN KEY(`UserID`) REFERENCES `User` (`UserID`),
    CONSTRAINT FOREIGN KEY(`BeanTypeID`) REFERENCES `BeanType` (`BeanTypeID`)
);

CREATE TABLE `Transactions` (
    `TransactionID` int NOT NULL AUTO_INCREMENT,
    `SenderID` int NOT NULL,
    `ReceiverID` int NOT NULL,
    `TransactionTypeID` int NOT NULL,
    `TransactionAmount` int NOT NULL,
    `TransactionTimestamp` datetime NOT NULL,
    PRIMARY KEY (`TransactionID`),
    CONSTRAINT FOREIGN KEY(`TransactionTypeID`) REFERENCES `TransactionsType` (`TransactionTypeID`)
);

ALTER TABLE
    BeanType
MODIFY
    ValueInRands DECIMAL(65, 2) NOT NULL;

ALTER TABLE
    Transactions
MODIFY
    TransactionAmount DECIMAL(65, 2) NOT NULL;

ALTER TABLE
    Account
MODIFY
    BalanceAmount DECIMAL(65, 2) NOT NULL;

INSERT INTO
    `TransactionsType` (`TransactionTypeID`, `TransactionTypeName`)
VALUES
    (1, 'Deposit'),
    (2, 'Withdrawal'),
    (3, 'Transfer');

INSERT INTO
    `BeanType` (`BeanName`, `BeanSymbol`, `ValueInRands`)
VALUES
    ('Green Bean', 'GNB', 5),
    ('Blue Bean', 'BLB', 10),
    ('Purple Bean', 'PLB', 15),
    ('Orange Bean', 'ORB', 20),
    ('Red Bean', 'RDB', 25),
    ('Yellow Bean', 'YLB', 30),
    ('Silver Bean', 'SLB', 35),
    ('Gold Bean', 'GLB', 40);