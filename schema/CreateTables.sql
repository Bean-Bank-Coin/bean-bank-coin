Use bean_bank_coin_db; 
CREATE TABLE `User` (
    `UserID` int  NOT NULL AUTO_INCREMENT,
    `UserName` varchar(120)  NOT NULL ,
    `Email` varchar(120)  NOT NULL ,
    `Password` varchar(120)  NOT NULL ,
    PRIMARY KEY (
        `UserID`
    ),
    CONSTRAINT `uc_User_UserName` UNIQUE (
        `UserName`
    ),
    CONSTRAINT `uc_User_Email` UNIQUE (
        `Email`
    )
);

CREATE TABLE `Account` (
    `AccountID` int  NOT NULL AUTO_INCREMENT,
    `UserID` int  NOT NULL ,
    `BeanTypeID` int  NOT NULL ,
    `BalanceAmount` decimal  NOT NULL ,
    `DateCreated` datetime(6)  NOT NULL ,
    `IsClosed` boolean  NOT NULL ,
    PRIMARY KEY (
        `AccountID`
    )
);

CREATE TABLE `BeanType` (
    `BeanTypeID` int  NOT NULL AUTO_INCREMENT,
    `BeanName` varchar(120)  NOT NULL ,
    `BeanSymbol` varchar(3)  NOT NULL ,
    `ValueInRands` int  NOT NULL ,
    PRIMARY KEY (
        `BeanTypeID`
    ),
    CONSTRAINT `uc_BeanType_BeanName` UNIQUE (
        `BeanName`
    ),
    CONSTRAINT `uc_BeanType_BeanSymbol` UNIQUE (
        `BeanSymbol`
    )
);

CREATE TABLE `Transactions` (
    `TransactionID` int  NOT NULL AUTO_INCREMENT,
    `SenderID` int  NOT NULL ,
    `ReceiverID` int  NOT NULL ,
    `TransactionTypeID` int  NOT NULL ,
    `TransactionAmount` int  NOT NULL ,
    `TransactionTimeStamp` datetime  NOT NULL , -- Adding CHECK constraint ,
    PRIMARY KEY (
        `TransactionID`
    )
);

CREATE TABLE `TransactionsType` (
    `TransactionTypeID` int  NOT NULL ,
    `TransactionTypeName` varchar(20)  NOT NULL ,
    PRIMARY KEY (
        `TransactionTypeID`
    )
);

ALTER TABLE `Account` ADD CONSTRAINT `fk_Account_UserID` FOREIGN KEY(`UserID`)
REFERENCES `User` (`UserID`);

ALTER TABLE `Account` ADD CONSTRAINT `fk_Account_BeanTypeID` FOREIGN KEY(`BeanTypeID`)
REFERENCES `BeanType` (`BeanTypeID`);

ALTER TABLE `Transactions` ADD CONSTRAINT `fk_Transactions_SenderID` FOREIGN KEY(`SenderID`)
REFERENCES `User` (`UserID`);

ALTER TABLE `Transactions` ADD CONSTRAINT `fk_Transactions_ReceiverID` FOREIGN KEY(`ReceiverID`)
REFERENCES `User` (`UserID`);

ALTER TABLE `Transactions` ADD CONSTRAINT `fk_Transactions_TransactionTypeID` FOREIGN KEY(`TransactionTypeID`)
REFERENCES `TransactionsType` (`TransactionTypeID`);
