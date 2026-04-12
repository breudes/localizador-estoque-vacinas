CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,

                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       cpf VARCHAR(11) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,

                       data_nasc DATE NOT NULL,
                       role VARCHAR(20) NOT NULL,

                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE addresses (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,

                           street VARCHAR(255) NOT NULL,
                           city VARCHAR(100) NOT NULL,
                           cep VARCHAR(8) NOT NULL,
                           state VARCHAR(2) NOT NULL,

                           created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                           updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                           user_id BIGINT UNIQUE,

                           CONSTRAINT fk_address_user
                               FOREIGN KEY (user_id)
                                   REFERENCES users(id)
                                   ON DELETE CASCADE
);