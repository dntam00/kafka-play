-- Create a stored procedure to generate 10,000 product operations
DELIMITER //

CREATE PROCEDURE bulk_product_operations()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE operation INT;

    -- Create 10,000 products
    WHILE i <= 10000 DO
        INSERT INTO products(name, description, quantity)
        VALUES (CONCAT('Product-', i), CONCAT('Description for product ', i), FLOOR(RAND() * 1000));
        SET i = i + 1;
END WHILE;

    -- Update 10,000 products
UPDATE products
SET quantity = quantity + 50,
    description = CONCAT(description, ' - Updated')
WHERE id BETWEEN 1 AND 10000;

-- Delete half of the products (5,000)
DELETE FROM products WHERE id <= 5000;

END //

DELIMITER ;

-- Call the procedure to execute the operations
CALL bulk_product_operations();

-- Drop the procedure if you want to clean up
-- DROP PROCEDURE bulk_product_operations;