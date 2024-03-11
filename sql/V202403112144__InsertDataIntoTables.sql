Use bean_bank_coin_schema;

INSERT INTO `User` (`UserName`, `Email`, `Password`)
VALUES
    ('JakeCole', 'jake@example.com', 'password123'),
    ('JessicaSmith', 'jessica@yahoo.com', 'securepass'),
    ('MichaelJones', 'michael@gmail.com', 'pass123'),
    ('SarahWilliams', 'sarah@bbd.com', 'password321'),
    ('DavidClark', 'david@apple.com', 'davidpass'),
    ('EmilyTaylor', 'emily@standarbank.com', 'emilypass'),
    ('AlexCarter', 'alex@yahoo.com', 'passalex'),
    ('OliviaBrown', 'olivia@example.com', 'oliviapass'),
    ('WilliamJackson', 'william@examples.com', 'williampass'),
    ('SophiaAnderson', 'sophia@outlook.com', 'sophiapass');

INSERT INTO `BeanType` (`BeanName`, `BeanSymbol`, `ValueInRands`)
VALUES
    ('Green Bean', 'GNB', 5),
    ('Blue Bean', 'BLB', 10),
    ('Purple Bean', 'PLB', 15),
    ('Orange Bean', 'ORB', 20),
    ('Red Bean', 'RDB', 25),
    ('Yellow Bean', 'YLB', 30),
    ('Silver Bean', 'SLB', 35),
    ('Gold Bean', 'GLB', 40);

INSERT INTO `Account` (`UserID`, `BeanTypeID`, `BalanceAmount`, `DateCreated`, `IsClosed`)
VALUES
    (1, 1, 100.00, NOW(), 0),
    (2, 2, 150.00, NOW(), 0),
    (3, 3, 200.00, NOW(), 0),
    (4, 4, 250.00, NOW(), 1),
    (5, 5, 300.00, NOW(), 0),
    (6, 6, 350.00, NOW(), 0),
    (7, 7, 400.00, NOW(), 1),
    (8, 8, 450.00, NOW(), 0),
    (9, 1, 500.00, NOW(), 0),
    (10, 5, 550.00, NOW(), 1);

INSERT INTO `TransactionsType` (`TransactionTypeID`, `TransactionTypeName`)
VALUES
    (1, 'Deposit'),
    (2, 'Withdrawal'),
    (3, 'Transfer');

INSERT INTO `Transactions` (`SenderID`, `ReceiverID`, `TransactionTypeID`, `TransactionAmount`, `TransactionTimeStamp`)
VALUES
    (1, 2, 1, 50, NOW()),
    (2, 3, 2, 60, NOW()),
    (3, 4, 3, 70, NOW()),
    (4, 5, 1, 80, NOW()),
    (5, 6, 2, 90, NOW()),
    (6, 7, 3, 100, NOW()),
    (7, 8, 1, 110, NOW()),
    (8, 9, 2, 120, NOW()),
    (9, 10, 3, 130, NOW()),
    (10, 1, 1, 140, NOW());