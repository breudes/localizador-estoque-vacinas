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