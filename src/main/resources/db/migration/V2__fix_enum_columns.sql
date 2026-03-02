-- Fix enum columns to use VARCHAR consistently
-- V2__fix_enum_columns.sql

-- The issue is that Hibernate expects enums as SMALLINT but we defined them as VARCHAR
-- We need to tell Hibernate to use VARCHAR for enums via @Enumerated(EnumType.STRING)
-- OR we can keep the migration and update the entity classes

-- For now, we'll update the entity classes to use STRING enumeration
-- This migration is a placeholder to document the fix needed

-- If you want to keep using the existing database, run these commands manually:
-- ALTER TABLE addresses ALTER COLUMN type TYPE VARCHAR(50);
-- ALTER TABLE orders ALTER COLUMN status TYPE VARCHAR(50);
-- ALTER TABLE orders ALTER COLUMN payment_status TYPE VARCHAR(50);
-- ALTER TABLE orders ALTER COLUMN payment_method TYPE VARCHAR(50);
-- ALTER TABLE carts ALTER COLUMN status TYPE VARCHAR(50);
-- ALTER TABLE reviews ALTER COLUMN status TYPE VARCHAR(50);
-- ALTER TABLE users ALTER COLUMN role TYPE VARCHAR(50);
-- ALTER TABLE users ALTER COLUMN status TYPE VARCHAR(50);
-- ALTER TABLE products ALTER COLUMN status TYPE VARCHAR(50);
