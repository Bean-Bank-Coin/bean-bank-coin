Use bean_bank_coin_schema;

INSERT INTO `Account` (`UserID`, `BeanTypeID`, `BalanceAmount`, `DateCreated`, `IsClosed`)
VALUES
    (1, 1, 100.00, NOW(), 0),
    (2, 2, 150.00, NOW(), 0),
    (3, 3, 200.00, NOW(), 0),
    (4, 4, 240.00, NOW(), 1),
    (5, 5, 300.00, NOW(), 0),
    (6, 6, 360.00, NOW(), 0),
    (7, 7, 70.00, NOW(), 1),
    (8, 8, 480.00, NOW(), 0),
    (9, 1, 500.00, NOW(), 0),
    (10, 5, 550.00, NOW(), 1),
    (5, 5, 300.00, NOW(), 0),
    (6, 3, 350.00, NOW(), 0),
    (7, 7, 700.00, NOW(), 1),
    (8, 8, 400.00, NOW(), 0),
    (9, 1, 500.00, NOW(), 0),
    (10, 5, 550.00, NOW(), 1);