CREATE TABLE addresses (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,

                           street VARCHAR(255),
                           number VARCHAR(100) NOT NULL,
                           complement VARCHAR(100),
                           city VARCHAR(100),
                           cep VARCHAR(8) NOT NULL,
                           state VARCHAR(2),

                           created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                           updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                           user_id BIGINT UNIQUE,

                           CONSTRAINT fk_address_user
                               FOREIGN KEY (user_id)
                                   REFERENCES users(id)
                                   ON DELETE CASCADE

                           health_facility_id BIGINT UNIQUE,

                           CONSTRAINT fk_address_health_facility
                               FOREIGN KEY (health_facility_id)
                                   REFERENCES health_facilities(id)
                                   ON DELETE CASCADE
);