CREATE TABLE inventories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    stock INT NOT NULL,
    vaccine_id BIGINT NOT NULL,
    health_facility_id BIGINT NOT NULL,
    batch VARCHAR(100) NOT NULL,
    expiration_date DATE NOT NULL,
    active BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (vaccine_id) REFERENCES vaccines(id),
    FOREIGN KEY (health_facility_id) REFERENCES health_facilities(id)
);
