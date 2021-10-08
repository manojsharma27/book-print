
-- Populate product_info
INSERT INTO book.product_info
(id, created_on, modified_on, base_price, description, image_url, product_type)
VALUES
(0, current_timestamp, current_timestamp, 2000, 'We support printing of all types of books with different sizes and paper types.', 'https://images.unsplash.com/photo-1549122728-f519709caa9c', 'BOOK'),
