CREATE SCHEMA IF NOT EXISTS book;

-- create product_type enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'product_type') THEN
        CREATE TYPE product_type AS ENUM (
            'BOOK'
        );
    END IF;
END $$;

-- create shipping_status enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'shipping_status') THEN
        CREATE TYPE shipping_status AS ENUM (
            'ORDERED',
            'DISPATCHED',
            'OUT_FOR_DELIVERY',
            'DELIVERED'
        );
    END IF;
END $$;

-- create payment_status enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'payment_status') THEN
        CREATE TYPE payment_status AS ENUM (
            'FAILED',
            'SUCCESS'
        );
    END IF;
END $$;

-- create payment_method enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'payment_method') THEN
        CREATE TYPE payment_method AS ENUM (
            'CARD'
        );
    END IF;
END $$;

-- create order_status enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'order_status') THEN
        CREATE TYPE order_status AS ENUM (
            'ORDERED',
            'DESIGN',
            'PRINT',
            'ASSEMBLE',
            'REVIEW',
            'SHIPPED',
            'COMPLETED'
        );
    END IF;
END $$;

-- creating tables
CREATE TABLE book.cart (
    id uuid not null,
    created_on timestamp,
    modified_on timestamp,
    checked_out boolean,
    total numeric,
    customer_id uuid,
    primary key (id)
);

CREATE TABLE book.cart_product_mapping (
    id bigserial not null,
    created_on timestamp,
    modified_on timestamp,
    quantity int4,
    cart_id uuid,
    product_id uuid,
    primary key (id)
);

CREATE TABLE book.customer (
    id uuid not null,
    created_on timestamp,
    modified_on timestamp,
    address varchar(500),
    email varchar(100),
    firstname varchar(50),
    lastname varchar(50),
    password varchar(100),
    phone_number varchar(15),
    pincode int4,
    primary key (id)
);

CREATE TABLE book.order (
    id uuid not null,
    created_on timestamp,
    modified_on timestamp,
    amount numeric,
    shipping_cost numeric,
    order_status order_status,
    quantity int4,
    customer_id uuid,
    payment_details_id uuid,
    product_id uuid,
    shipment_id uuid,
    primary key (id)
);

CREATE TABLE book.payment_details (
    id uuid not null,
    created_on timestamp,
    modified_on timestamp,
    amount numeric,
    payment_method payment_method,
    payment_status payment_status,
    transaction_id varchar(50),
    primary key (id)
);

CREATE TABLE book.product (
    id uuid not null,
    created_on timestamp,
    modified_on timestamp,
    details_json jsonb not null,
    name varchar(100),
    price numeric,
    product_info_id int4 not null,
    primary key (id)
);

CREATE TABLE book.product_info (
    id serial not null,
    created_on timestamp,
    modified_on timestamp,
    base_price numeric,
    description varchar(1000),
    image_url varchar(1000),
    product_type product_type,
    primary key (id)
);

CREATE TABLE book.shipment (
    id uuid not null,
    created_on timestamp,
    modified_on timestamp,
    address varchar(500),
    pincode integer,
    shipping_status shipping_status,
    primary key (id)
);

-- creating indexes

CREATE INDEX cart_id_idx ON book.cart_product_mapping (cart_id);

CREATE INDEX order_customer_id_idx ON book.order (customer_id);

CREATE UNIQUE INDEX email_idx ON book.customer (email);

-- adding constraints

ALTER TABLE book.cart_product_mapping
    ADD CONSTRAINT cart_id_product_id_idx unique (cart_id, product_id);


ALTER TABLE book.cart
    ADD CONSTRAINT FK_customer_cart
    FOREIGN KEY (customer_id)
    REFERENCES book.customer;

ALTER TABLE book.cart_product_mapping
    ADD CONSTRAINT FK_cart_cart_product_mapping
    FOREIGN KEY (cart_id)
    REFERENCES book.cart;

ALTER TABLE book.cart_product_mapping
    ADD CONSTRAINT FK_product_cart_product_mapping
    FOREIGN KEY (product_id)
    REFERENCES book.product;

ALTER TABLE book.order
    ADD CONSTRAINT FK_customer_order
    FOREIGN KEY (customer_id)
    REFERENCES book.customer;

ALTER TABLE book.order
    ADD CONSTRAINT FK_payment_details_order
    FOREIGN KEY (payment_details_id)
    REFERENCES book.payment_details;

ALTER TABLE book.order
    ADD CONSTRAINT FK_product_order
    FOREIGN KEY (product_id)
    REFERENCES book.product;

ALTER TABLE book.order
    ADD CONSTRAINT FK_shipment_order
    FOREIGN KEY (shipment_id)
    REFERENCES book.shipment;

ALTER TABLE book.product
    ADD CONSTRAINT FK_product_info_product
    FOREIGN KEY (product_info_id)
    REFERENCES book.product_info;