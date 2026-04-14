-- Create database if not exists
IF NOT EXISTS (SELECT 1 FROM sys.databases WHERE name = 'TESTDB')
BEGIN
    CREATE DATABASE TESTDB;
END
GO

USE TESTDB;
GO

-- Create clients table if not exists
IF NOT EXISTS (
    SELECT 1
    FROM INFORMATION_SCHEMA.TABLES
    WHERE TABLE_SCHEMA = 'dbo'
      AND TABLE_NAME = 'clients'
)
BEGIN
    CREATE TABLE dbo.clients (
        id BIGINT IDENTITY(1,1) NOT NULL,
        client_name VARCHAR(150) NOT NULL,
        email VARCHAR(150) NOT NULL,
        status VARCHAR(20) NOT NULL,
        created_at DATETIME2 NOT NULL,
        updated_at DATETIME2 NULL,

        CONSTRAINT pk_clients PRIMARY KEY (id),
        CONSTRAINT uq_clients_email UNIQUE (email)
    );
END
GO