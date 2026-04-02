CREATE TABLE IF NOT EXISTS shift_types (
                                           id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    tenant_id VARCHAR(36) NOT NULL
    );

CREATE TABLE IF NOT EXISTS shifts (
                                      id VARCHAR(36) PRIMARY KEY,
    shift_type_id VARCHAR(36),
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    tenant_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (shift_type_id) REFERENCES shift_types(id)
    );

CREATE TABLE IF NOT EXISTS users (
                                     id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    is_logged_in BOOLEAN DEFAULT FALSE,
    timezone VARCHAR(50) DEFAULT 'UTC',
    tenant_id VARCHAR(36) NOT NULL
    );

CREATE TABLE IF NOT EXISTS shift_users (
                                           id VARCHAR(36) PRIMARY KEY,
    shift_id VARCHAR(36),
    user_id VARCHAR(36),
    tenant_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (shift_id) REFERENCES shifts(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
    );