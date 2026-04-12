CREATE TABLE health_facilities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cnes VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(50),
    address_id BIGINT UNIQUE,
    FOREIGN KEY (address_id) REFERENCES addresses(id)
);
