-- import.sql

-- Insert sample products
INSERT INTO Product (name, price, status, postedDate) VALUES ('Product 1', 100.00, 'ACTIVE', CURRENT_TIMESTAMP);
INSERT INTO Product (name, price, status, postedDate) VALUES ('Product 2', 150.00, 'ACTIVE', CURRENT_TIMESTAMP);
INSERT INTO Product (name, price, status, postedDate) VALUES ('Product 3', 200.00, 'ACTIVE', CURRENT_TIMESTAMP);

-- Insert sample products in the approval queue
INSERT INTO ApprovalQueue (product_id, requestDate) VALUES (1, CURRENT_TIMESTAMP);
INSERT INTO ApprovalQueue (product_id, requestDate) VALUES (2, CURRENT_TIMESTAMP);
