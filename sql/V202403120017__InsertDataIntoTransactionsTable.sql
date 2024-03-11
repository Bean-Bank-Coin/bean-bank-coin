Use bean_bank_coin_schema;

INSERT INTO `Transactions` (`SenderID`, `ReceiverID`, `TransactionTypeID`, `TransactionAmount`, `TransactionTimeStamp`)
VALUES
    (1, 1, 1, 50, NOW()),
    (2, 2, 2, 60, NOW()),
    (3, 4, 3, 70, NOW()),
    (4, 4, 1, 80, NOW()),
    (5, 5, 2, 90, NOW()),
    (6, 7, 3, 100, NOW()),
    (7, 7, 1, 110, NOW()),
    (8, 8, 2, 120, NOW()),
    (9, 10, 3, 130, NOW()),
    (10, 10, 1, 140, NOW()),
    (5, 5, 2, 150, NOW()),
    (6, 3, 3, 160, NOW()),
    (7, 7, 1, 170, NOW()),
    (8, 8, 2, 180, NOW()),
    (9, 1, 3, 190, NOW()),
    (10, 5, 1, 200, NOW());