-- Create a stored procedure to generate 10,000 product operations
USE inventory;

DROP PROCEDURE IF EXISTS bulk_product_operations;

DELIMITER //

CREATE PROCEDURE bulk_product_operations()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE category_count INT DEFAULT 500;
    DECLARE category_index INT;
    DECLARE category_name VARCHAR(255);

    -- Create 10,000 products with random categories
    WHILE i <= 10000 DO
        -- Calculate a random index between 1 and 500 for the category
        SET category_index = FLOOR(1 + (RAND() * category_count));

        -- Generate category name based on index
CASE
            WHEN category_index <= 50 THEN SET category_name = CONCAT('Electronics-', category_index);
WHEN category_index <= 100 THEN SET category_name = CONCAT('Clothing-', category_index - 50);
WHEN category_index <= 150 THEN SET category_name = CONCAT('Home-', category_index - 100);
WHEN category_index <= 200 THEN SET category_name = CONCAT('Kitchen-', category_index - 150);
WHEN category_index <= 250 THEN SET category_name = CONCAT('Books-', category_index - 200);
WHEN category_index <= 300 THEN SET category_name = CONCAT('Sports-', category_index - 250);
WHEN category_index <= 350 THEN SET category_name = CONCAT('Toys-', category_index - 300);
WHEN category_index <= 400 THEN SET category_name = CONCAT('Beauty-', category_index - 350);
WHEN category_index <= 450 THEN SET category_name = CONCAT('Health-', category_index - 400);
ELSE SET category_name = CONCAT('Automotive-', category_index - 450);
END CASE;

INSERT INTO products(name, description, category, quantity)
VALUES (
           CONCAT('Product-', i),
           CONCAT('Description for product ', i),
           category_name,
           FLOOR(RAND() * 1000)
       );

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