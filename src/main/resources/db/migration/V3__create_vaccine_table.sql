CREATE TABLE vaccines (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    vaccine_name VARCHAR(255) NOT NULL,
    prevented_disease VARCHAR(255),
    observation VARCHAR(255),
    initial_age_in_months INT,
    final_age_in_months INT,
    initial_age_in_years INT,
    final_age_in_years INT,
    life_stage VARCHAR(100),
    dose_quantity INT NOT NULL,
    booster BOOLEAN DEFAULT FALSE,
    single_dose BOOLEAN DEFAULT FALSE,
    interval_in_days INT,
    active BOOLEAN DEFAULT FALSE
);
