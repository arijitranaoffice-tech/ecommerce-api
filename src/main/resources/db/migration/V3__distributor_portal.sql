-- Distributor Portal Schema
-- V3__distributor_portal.sql

-- Distributors table
CREATE TABLE distributors (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    distributor_code VARCHAR(100) NOT NULL UNIQUE,
    company_name VARCHAR(255) NOT NULL,
    business_license_number VARCHAR(100),
    tax_id VARCHAR(100),
    business_address VARCHAR(1000),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    commission_rate DECIMAL(5, 2) NOT NULL DEFAULT 5.00,
    tier VARCHAR(50) NOT NULL DEFAULT 'BRONZE',
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    credit_limit DECIMAL(12, 2) NOT NULL DEFAULT 10000.00,
    current_balance DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    contract_start_date DATE,
    contract_end_date DATE,
    territory_description VARCHAR(2000),
    created_by UUID,
    last_modified_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0
);

-- Distributor territories table
CREATE TABLE distributor_territories (
    distributor_id UUID NOT NULL REFERENCES distributors(id) ON DELETE CASCADE,
    territory_code VARCHAR(100) NOT NULL,
    PRIMARY KEY (distributor_id, territory_code)
);

-- Warehouses table
CREATE TABLE warehouses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    distributor_id UUID REFERENCES distributors(id) ON DELETE SET NULL,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(100) NOT NULL UNIQUE,
    address VARCHAR(500) NOT NULL,
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    phone VARCHAR(50),
    email VARCHAR(255),
    description VARCHAR(1000),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_by UUID,
    last_modified_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0
);

-- Distributor products table
CREATE TABLE distributor_products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    distributor_id UUID NOT NULL REFERENCES distributors(id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    distributor_price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    minimum_order_quantity DECIMAL(10, 2) NOT NULL DEFAULT 1.00,
    maximum_order_quantity DECIMAL(10, 2) NOT NULL DEFAULT 1000.00,
    stock_allocation INTEGER NOT NULL DEFAULT 0,
    reserved_stock INTEGER NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    valid_from TIMESTAMP,
    valid_until TIMESTAMP,
    notes VARCHAR(500),
    tier1_discount DECIMAL(5, 2) NOT NULL DEFAULT 0.00,
    tier2_discount DECIMAL(5, 2) NOT NULL DEFAULT 0.00,
    tier3_discount DECIMAL(5, 2) NOT NULL DEFAULT 0.00,
    tier1_threshold INTEGER NOT NULL DEFAULT 10,
    tier2_threshold INTEGER NOT NULL DEFAULT 50,
    tier3_threshold INTEGER NOT NULL DEFAULT 100,
    created_by UUID,
    last_modified_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    UNIQUE (distributor_id, product_id)
);

-- Distributor orders table
CREATE TABLE distributor_orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    distributor_id UUID NOT NULL REFERENCES distributors(id) ON DELETE CASCADE,
    order_id UUID REFERENCES orders(id) ON DELETE SET NULL,
    distributor_order_number VARCHAR(100) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    subtotal DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    tax_amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    shipping_amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    discount_amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    total_amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    commission_amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    payment_status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    payment_method VARCHAR(50) NOT NULL DEFAULT 'COD',
    shipping_address_id UUID REFERENCES addresses(id),
    billing_address_id UUID REFERENCES addresses(id),
    distributor_note TEXT,
    internal_note TEXT,
    approved_at TIMESTAMP,
    shipped_at TIMESTAMP,
    delivered_at TIMESTAMP,
    cancelled_at TIMESTAMP,
    cancellation_reason TEXT,
    rejection_reason TEXT,
    is_fulfillment_order BOOLEAN NOT NULL DEFAULT FALSE,
    tracking_number VARCHAR(255),
    carrier VARCHAR(100),
    created_by UUID,
    last_modified_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0
);

-- Distributor order items table
CREATE TABLE distributor_order_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    distributor_order_id UUID NOT NULL REFERENCES distributor_orders(id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    distributor_product_id UUID REFERENCES distributor_products(id) ON DELETE SET NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    discount_percent DECIMAL(5, 2) NOT NULL DEFAULT 0.00,
    tax_rate DECIMAL(5, 2) NOT NULL DEFAULT 0.00,
    total_price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    notes VARCHAR(500),
    fulfilled_quantity INTEGER DEFAULT 0,
    returned_quantity INTEGER DEFAULT 0,
    created_by UUID,
    last_modified_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0
);

-- Distributor commissions table
CREATE TABLE distributor_commissions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    distributor_id UUID NOT NULL REFERENCES distributors(id) ON DELETE CASCADE,
    order_id UUID REFERENCES orders(id) ON DELETE SET NULL,
    distributor_order_id UUID REFERENCES distributor_orders(id) ON DELETE SET NULL,
    amount DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    rate DECIMAL(5, 2) NOT NULL DEFAULT 0.00,
    type VARCHAR(50) NOT NULL DEFAULT 'SALE',
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    period_start_date DATE,
    period_end_date DATE,
    calculated_at TIMESTAMP,
    paid_at TIMESTAMP,
    notes TEXT,
    is_paid BOOLEAN NOT NULL DEFAULT FALSE,
    payment_reference VARCHAR(255),
    created_by UUID,
    last_modified_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0
);

-- Distributor inventory table
CREATE TABLE distributor_inventory (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    distributor_id UUID NOT NULL REFERENCES distributors(id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    warehouse_id UUID REFERENCES warehouses(id) ON DELETE SET NULL,
    quantity_on_hand INTEGER NOT NULL DEFAULT 0,
    quantity_available INTEGER NOT NULL DEFAULT 0,
    quantity_reserved INTEGER NOT NULL DEFAULT 0,
    quantity_in_transit INTEGER NOT NULL DEFAULT 0,
    reorder_point INTEGER NOT NULL DEFAULT 10,
    reorder_quantity INTEGER NOT NULL DEFAULT 50,
    last_restocked_at TIMESTAMP,
    location VARCHAR(100),
    bin VARCHAR(50),
    notes VARCHAR(500),
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_by UUID,
    last_modified_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0,
    UNIQUE (distributor_id, product_id, warehouse_id)
);

-- Inventory transfers table
CREATE TABLE inventory_transfers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    distributor_id UUID NOT NULL REFERENCES distributors(id) ON DELETE CASCADE,
    product_id UUID NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    from_warehouse_id UUID REFERENCES warehouses(id) ON DELETE SET NULL,
    to_warehouse_id UUID REFERENCES warehouses(id) ON DELETE SET NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    transfer_date TIMESTAMP,
    received_date TIMESTAMP,
    reason VARCHAR(500),
    notes VARCHAR(500),
    tracking_number VARCHAR(255),
    created_by UUID,
    last_modified_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0
);

-- Distributor customers table
CREATE TABLE distributor_customers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    distributor_id UUID NOT NULL REFERENCES distributors(id) ON DELETE CASCADE,
    customer_user_id UUID REFERENCES users(id) ON DELETE SET NULL,
    company_name VARCHAR(255) NOT NULL,
    contact_person VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(50),
    address VARCHAR(500),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    tier VARCHAR(50) NOT NULL DEFAULT 'STANDARD',
    credit_limit DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    current_balance DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    last_order_date DATE,
    total_orders INTEGER NOT NULL DEFAULT 0,
    total_revenue DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    notes VARCHAR(500),
    created_by UUID,
    last_modified_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0
);

-- Distributor customer tags table
CREATE TABLE distributor_customer_tags (
    distributor_customer_id UUID NOT NULL REFERENCES distributor_customers(id) ON DELETE CASCADE,
    tag VARCHAR(100) NOT NULL,
    PRIMARY KEY (distributor_customer_id, tag)
);

-- Indexes for distributor tables
CREATE INDEX idx_distributors_user ON distributors(user_id);
CREATE INDEX idx_distributors_code ON distributors(distributor_code);
CREATE INDEX idx_distributors_status ON distributors(status);
CREATE INDEX idx_distributors_tier ON distributors(tier);
CREATE INDEX idx_warehouses_distributor ON warehouses(distributor_id);
CREATE INDEX idx_warehouses_code ON warehouses(code);
CREATE INDEX idx_distributor_products_distributor ON distributor_products(distributor_id);
CREATE INDEX idx_distributor_products_product ON distributor_products(product_id);
CREATE INDEX idx_distributor_orders_distributor ON distributor_orders(distributor_id);
CREATE INDEX idx_distributor_orders_status ON distributor_orders(status);
CREATE INDEX idx_distributor_order_items_order ON distributor_order_items(distributor_order_id);
CREATE INDEX idx_distributor_commissions_distributor ON distributor_commissions(distributor_id);
CREATE INDEX idx_distributor_commissions_status ON distributor_commissions(status);
CREATE INDEX idx_distributor_inventory_distributor ON distributor_inventory(distributor_id);
CREATE INDEX idx_distributor_inventory_product ON distributor_inventory(product_id);
CREATE INDEX idx_distributor_inventory_warehouse ON distributor_inventory(warehouse_id);
CREATE INDEX idx_inventory_transfers_distributor ON inventory_transfers(distributor_id);
CREATE INDEX idx_distributor_customers_distributor ON distributor_customers(distributor_id);
CREATE INDEX idx_distributor_customers_status ON distributor_customers(status);

-- Add DISTRIBUTOR to user_role enum (if using PostgreSQL enums)
-- Note: This is handled in the application code for JPA EnumType.STRING
