DROP DATABASE IF EXISTS test_db;

CREATE DATABASE test_db;

USE test_db;
-- -----------------------------------------------------------------------------------

-- CREATE TABLE admin (
--    `id` INT(10) PRIMARY KEY AUTO_INCREMENT,
--    `login_id` VARCHAR(20) UNIQUE,
--    `password` VARCHAR(64),
--    `actived_flag` INT(1) DEFAULT 1,
--    `reset_password_token` VARCHAR(100),
--    `updated` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
--    `created` datetime DEFAULT current_timestamp()
-- );
--
-- INSERT INTO `admin` (`login_id`, `password`, `actived_flag`, `reset_password_token`) VALUES
--     ('admin1', md5('admin1'), 1, 'reset_token_value_1'),
--     ('admin2', md5('admin2'), 0, 'reset_token_value_2'),
--     ('admin3', md5('admin3'), 1, '');

-- ---------------------------------------------------------------------------------------
CREATE TABLE customer (
    cus_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    birth DATE NOT NULL,
    cic VARCHAR(50) NOT NULL CHECK (LENGTH(cic) = 12),
    address VARCHAR (500) DEFAULT NULL,
    phone Varchar(15) DEFAULT NULL,
    email VARCHAR(255) DEFAULT NULL,
    facebook LONGTEXT DEFAULT NULL,
    status int DEFAULT 1 CHECK (status IN (1, 0)),
    updated datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    created datetime DEFAULT current_timestamp()
);

INSERT INTO customer (name, gender, birth, cic, address, phone, email, facebook) VALUES
    ('Nguyễn Văn A', 'Male', '1990-05-15', '231412341231', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123456789', 'nguyenvana@example.com', 'https://www.facebook.com/nguyenvana'),
    ('Trần Thị B', 'Female', '1985-09-20', '987654321564', '456 Đường Nguyễn Huệ, Quận 3, Thành phố Hồ Chí Minh', '0987654321', 'tranthib@example.com', 'https://www.facebook.com/tranthib'),
    ('Phạm Văn C', 'Male', '1982-03-10', '456123789525', '789 Đường Lê Duẩn, Quận 5, Thành phố Hồ Chí Minh', '0456123789', 'phamvanc@example.com', 'https://www.facebook.com/phamvanc'),
    ('Lê Thị D', 'Female', '1995-07-25', '987654321479', '456 Đường Nguyễn Huệ, Quận 3, Thành phố Hồ Chí Minh', '0987654321', 'lethid@example.com', 'https://www.facebook.com/lethid'),
    ('Hoàng Văn E', 'Male', '1988-12-02', '123456789987', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123456789', 'hoangvane@example.com', 'https://www.facebook.com/hoangvane'),
    ('Mai Thị F', 'Female', '1993-06-30', '456123789754', '789 Đường Lê Duẩn, Quận 5, Thành phố Hồ Chí Minh', '0456123789', 'maithif@example.com', 'https://www.facebook.com/maithif'),
    ('Trương Văn G', 'Male', '1980-08-18', '123456789685', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123456789', 'truongvang@example.com', 'https://www.facebook.com/truongvang'),
    ('Phan Thị H', 'Female', '1987-04-05', '987654321854', '456 Đường Nguyễn Huệ, Quận 3, Thành phố Hồ Chí Minh', '0987654321', 'phanthih@example.com', 'https://www.facebook.com/phanthih'),
    ('Nguyễn Văn I', 'Male', '1991-11-11', '456123789653', '789 Đường Lê Duẩn, Quận 5, Thành phố Hồ Chí Minh', '0456123789', 'nguyenvani@example.com', 'https://www.facebook.com/nguyenvani'),
    ('Lê Thị K', 'Female', '1984-02-28', '123456789684', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123456789', 'lethik@example.com', 'https://www.facebook.com/lethik'),
    ('Trương Văn GG', 'Male', '1980-08-18', '123456789765', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123456789', 'truongvang@example.com', 'https://www.facebook.com/truongvang'),
    ('Phan Thị HH', 'Female', '1987-04-05', '987654321675', '456 Đường Nguyễn Huệ, Quận 3, Thành phố Hồ Chí Minh', '0987654321', 'phanthih@example.com', 'https://www.facebook.com/phanthih'),
    ('Nguyễn Văn II', 'Male', '1991-11-11', '456123789434', '789 Đường Lê Duẩn, Quận 5, Thành phố Hồ Chí Minh', '0456123789', 'nguyenvani@example.com', 'https://www.facebook.com/nguyenvani'),
    ('Lê Thị KK', 'Female', '1984-02-28', '123456789342', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123456789', 'lethik@example.com', 'https://www.facebook.com/lethik');

ALTER TABLE customer
    ADD CONSTRAINT unique_cic UNIQUE (cic);

-- ------------------------------------------------------------------------------------------
CREATE TABLE category(
    cat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    label_name VARCHAR(255) NOT NULL,
    updated datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    created datetime DEFAULT current_timestamp()
);

ALTER TABLE category
    ADD CONSTRAINT unique_label_name UNIQUE (label_name);

CREATE TABLE group_category(
    grp_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_name VARCHAR(255) NOT NULL,
    updated datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    created datetime DEFAULT current_timestamp()
);

ALTER TABLE group_category
    ADD CONSTRAINT unique_group_name UNIQUE (group_name);

CREATE TABLE rel_grp_cat(
    grp_id BIGINT NOT NULL,
    cat_id BIGINT NOT NULL,
    PRIMARY KEY (grp_id, cat_id)
);

CREATE TABLE rel_cus_cat(
    cut_id BIGINT NOT NULL,
    cat_id BIGINT NOT NULL,
    PRIMARY KEY (cut_id, cat_id)
);

-- Thêm dữ liệu cho bảng category
INSERT INTO category (label_name) VALUES
    ('Có trên 1 tỷ'),
    ('Bố làm to'),
    ('Thành tích học tập'),
    ('Xe hơi sang trọng'),
    ('Sở hữu nhà đất'),
    ('Hưởng thụ cuộc sống cao cấp'),
    ('Du lịch nước ngoài'),
    ('Thực hiện các hoạt động từ thiện'),
    ('Sở hữu các sản phẩm hàng hiệu'),
    ('Sử dụng dịch vụ cao cấp');

-- Thêm dữ liệu cho bảng group_category
INSERT INTO group_category (group_name) VALUES
    ('Có tiềm lực tài chính'),
    ('Đặc quyền và địa vị xã hội'),
    ('Thành đạt và thành công'),
    ('Phong cách sống cao cấp');


INSERT INTO rel_grp_cat (grp_id, cat_id) VALUES
    (1, 2),
    (1, 4),
    (1, 5),

    (2, 2),
    (2, 3),
    (2, 8),

    (3, 3),
    (3, 6),

    (4, 4),
    (4, 6),
    (4, 8),
    (4, 9),
    (4, 10);



INSERT INTO rel_cus_cat (cut_id, cat_id) VALUES
    (11, 1),
    (2, 1),
    (3, 2),
    (4, 2),
    (5, 3),
    (12, 3),
    (14, 3),
    (3, 4),
    (2, 4),
    (4, 4),
    (6, 4),
    (5, 5),
    (4, 5),
    (8, 5),
    (9, 6),
    (8, 6),
    (10, 7),
    (11, 7),
    (12, 7),
    (5, 8),
    (2, 9),
    (13, 9),
    (14, 10),
    (11, 10),
    (12, 10);

-- foreign key
ALTER TABLE rel_cus_cat
    ADD CONSTRAINT fk_cus_cat_customer FOREIGN KEY (cut_id) REFERENCES customer(cus_id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT fk_cus_cat_category FOREIGN KEY (cat_id) REFERENCES category(cat_id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE rel_grp_cat
    ADD CONSTRAINT fk_grp_cat_group_category FOREIGN KEY (grp_id) REFERENCES group_category(grp_id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT fk_grp_cat_category FOREIGN KEY (cat_id) REFERENCES category(cat_id) ON DELETE CASCADE ON UPDATE CASCADE;
