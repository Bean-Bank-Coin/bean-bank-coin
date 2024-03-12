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