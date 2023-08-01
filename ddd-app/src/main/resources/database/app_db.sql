CREATE TABLE IF NOT EXISTS user_model (
    id CHAR(64) NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);