-- 数据库初始化脚本
-- H2内存数据库会自动执行此脚本

-- 创建用户表
CREATE TABLE IF NOT EXISTS t_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    phone VARCHAR(20) NOT NULL,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50) NOT NULL,
    id_type VARCHAR(20) NOT NULL,
    id_number VARCHAR(50) NOT NULL,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login_time TIMESTAMP
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_phone ON t_user(phone);

-- 创建申报表
CREATE TABLE IF NOT EXISTS t_submission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    category VARCHAR(20) NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    applicant_type VARCHAR(20) NOT NULL,
    recommend_unit VARCHAR(200),
    seal_file VARCHAR(500),
    work_link VARCHAR(500),
    work_files TEXT,
    id_card VARCHAR(50),
    id_card_image VARCHAR(500),
    status VARCHAR(20) DEFAULT 'draft',
    submit_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_user_id ON t_submission(user_id);
CREATE INDEX IF NOT EXISTS idx_status ON t_submission(status);
