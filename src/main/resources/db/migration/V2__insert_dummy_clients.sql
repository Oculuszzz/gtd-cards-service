USE TESTDB;
GO

DECLARE @i INT = 1;

WHILE @i <= 120
BEGIN
    INSERT INTO dbo.clients (
        client_name,
        email,
        status,
        created_at,
        updated_at
    )
    VALUES (
        CONCAT('Client ', @i),
        CONCAT('client', @i, '@example.com'),
        CASE
            WHEN @i % 3 = 0 THEN 'SUSPENDED'
            WHEN @i % 2 = 0 THEN 'INACTIVE'
            ELSE 'ACTIVE'
        END,
        SYSDATETIME(),
        SYSDATETIME()
    );

    SET @i = @i + 1;
END
GO