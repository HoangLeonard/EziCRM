DROP DATABASE IF EXISTS test_db;

CREATE DATABASE test_db;

USE test_db;
CREATE TABLE customer (
    cus_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    address VARCHAR (100) DEFAULT NULL,
    birth DATE DEFAULT NULL,
    phone Varchar(20) DEFAULT NULL UNIQUE, -- unique
    email VARCHAR(50) DEFAULT NULL UNIQUE, -- unique
    facebook VARCHAR(100) DEFAULT NULL UNIQUE, -- unique
    updated datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    created datetime DEFAULT current_timestamp(),
    CONSTRAINT at_least_one_contact_method_not_null
        CHECK ( phone IS NOT NULL OR email IS NOT NULL OR facebook IS NOT NULL )
);

INSERT INTO customer (name, birth, address, phone, email, facebook) VALUES
    ('Nguyễn Văn A', '1990-05-15', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0183456789', 'nguyenvana@example.com', 'https://www.facebook.com/nguyenvana'),
    ('Trần Thị B', '1985-09-20', '456 Đường Nguyễn Huệ, Quận 3, Thành phố Hồ Chí Minh', '0287654321', 'tranthib@example.com', 'https://www.facebook.com/tranthib'),
    ('Phạm Văn C', '1982-03-10', '789 Đường Lê Duẩn, Quận 5, Thành phố Hồ Chí Minh', '0456143789', 'phamvanc@example.com', 'https://www.facebook.com/phamvanc'),
    ('Lê Thị D', '1995-07-25', '456 Đường Nguyễn Huệ, Quận 3, Thành phố Hồ Chí Minh', '0987624321', 'lethid@example.com', 'https://www.facebook.com/lethid'),
    ('Hoàng Văn E', '1988-12-02', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123556719', 'hoangvane@example.com', 'https://www.facebook.com/hoangvane'),
    ('Mai Thị F', '1993-06-30', '789 Đường Lê Duẩn, Quận 5, Thành phố Hồ Chí Minh', '0456123389', 'maithif@example.com', 'https://www.facebook.com/maithif'),
    ('Trương Văn G', '1980-08-18', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123456789', 'truongvang2@example.com', 'https://www.facebook.com/truongvang2'),
    ('Phan Thị H', '1987-04-05', '456 Đường Nguyễn Huệ, Quận 3, Thành phố Hồ Chí Minh', '0987654321', 'phanth1h@example.com', 'https://www.facebook.com/phanth1h'),
    ('Nguyễn Văn I', '1991-11-11', '789 Đường Lê Duẩn, Quận 5, Thành phố Hồ Chí Minh', '0456122789', 'nguyenvan1@example.com', 'https://www.facebook.com/nguyenvan1'),
    ('Lê Thị K', '1984-02-28', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123456389', 'lethik1@example.com', 'https://www.facebook.com/lethik1'),
    ('Trương Văn GG', '1980-08-18', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123446789', 'truongvang1@example.com', 'https://www.facebook.com/truongvang1'),
    ('Phan Thị HH', '1987-04-05', '456 Đường Nguyễn Huệ, Quận 3, Thành phố Hồ Chí Minh', '0987360321', 'phanth2h@example.com', 'https://www.facebook.com/phanthih'),
    ('Nguyễn Văn II', '1991-11-11', '789 Đường Lê Duẩn, Quận 5, Thành phố Hồ Chí Minh', '0456123789', 'nguyenvani@example.com', 'https://www.facebook.com/nguyenvani'),
    ('Lê Thị KK', '1984-02-28', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123401789', 'lethik@example.com', 'https://www.facebook.com/lethik');

-- ------------------------------------------------------------------------------------------
CREATE TABLE category(
    cat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE,
    updated datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    created datetime DEFAULT current_timestamp()
);

CREATE TABLE rel_cus_cat(
    cus_id BIGINT NOT NULL,
    cat_id BIGINT NOT NULL,
    PRIMARY KEY (cus_id, cat_id)
);

-- Thêm dữ liệu cho bảng category
INSERT INTO category (category_name) VALUES
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

INSERT INTO rel_cus_cat (cus_id, cat_id) VALUES
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
    ADD CONSTRAINT fk_cus_cat_customer FOREIGN KEY (cus_id) REFERENCES customer(cus_id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT fk_cus_cat_category FOREIGN KEY (cat_id) REFERENCES category(cat_id) ON DELETE CASCADE ON UPDATE CASCADE;


-- temporary
# CREATE TABLE tmp_customer
# (
#     id       BIGINT AUTO_INCREMENT PRIMARY KEY,
#     name     VARCHAR(40) NOT NULL,
#     address  VARCHAR(100) DEFAULT NULL,
#     birth    DATE         DEFAULT NULL,
#     phone    Varchar(20)  DEFAULT NULL,
#     email    VARCHAR(50)  DEFAULT NULL,
#     facebook VARCHAR(100) DEFAULT NULL
# );
#
# CREATE TABLE error_info
# (
#     id BIGINT AUTO_INCREMENT PRIMARY KEY,
#     error VARCHAR(100) NOT NULL
# );
#
# INSERT INTO error_info (id, error) VALUES
#     (1, 'Invalid name format, '),
#     (2, 1),
#     (3, 2),