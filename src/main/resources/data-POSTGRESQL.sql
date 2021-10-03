
-- Populate product_type
INSERT INTO book.product_type
(id, "type")
VALUES
(0, 'BOOK');


-- Populate product_info
INSERT INTO book.product_info
(id, created_on, modified_on, base_price, description, image_url, product_type_id)
VALUES
(0, current_timestamp, current_timestamp, 2000, 'We support printing of all types of books with different sizes and paper types.', 'https://images.unsplash.com/photo-1549122728-f519709caa9c', 0);


-- Populate shipping_status
INSERT INTO book.shipping_status
(id, "status", shipping_code)
VALUES
(0,'ORDERED', 'SHIP_ORD'),
(1,'DISPATCHED', 'SHIP_DISP'),
(2,'OUT_FOR_DELIVERY', 'SHIP_OUT'),
(3,'DELIVERED', 'SHIP_DELV');


-- Populate payment_status
INSERT INTO book.payment_status
(id, "status", status_code)
VALUES
(0,'NOT_PAID', 'PAY_NO');
(1,'IN_PROGRESS', 'PAY_PROG');
(2,'FAILED', 'PAY_FAIL');
(3,'SUCCESS', 'PAY_YES');


-- Populate payment_method
INSERT INTO book.payment_method
(id, "method")
VALUES
(0, 'CARD');


-- Populate order_status
INSERT INTO book.order_status
(id, "status")
VALUES
(0, 'ORDERED'),
(1, 'DESIGN'),
(2, 'PRINT'),
(3, 'ASSEMBLE'),
(4, 'REVIEW'),
(5, 'SHIPPED'),
(6, 'COMPLETED');