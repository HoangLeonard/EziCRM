DROP DATABASE IF EXISTS test_db;

CREATE DATABASE test_db;

USE test_db;
-- -----------------------------------------------------------------------------------

CREATE TABLE admin (
   `id` INT(10) PRIMARY KEY AUTO_INCREMENT,
   `login_id` VARCHAR(20) UNIQUE,
   `password` VARCHAR(64),
   `actived_flag` INT(1) DEFAULT 1,
   `reset_password_token` VARCHAR(100),
   `updated` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
   `created` datetime DEFAULT current_timestamp()
);

INSERT INTO `admin` (`login_id`, `password`, `actived_flag`, `reset_password_token`) VALUES
    ('admin1', md5('admin1'), 1, 'reset_token_value_1'),
    ('admin2', md5('admin2'), 0, 'reset_token_value_2'),
    ('admin3', md5('admin3'), 1, '');

-- ---------------------------------------------------------------------------------------
CREATE TABLE customer (
    cus_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    birth DATE NOT NULL,
    cccd VARCHAR(50) NOT NULL,
    address VARCHAR (500) DEFAULT NULL,
    phone Varchar(15) DEFAULT NULL,
    email VARCHAR(255) DEFAULT NULL,
    facebook LONGTEXT DEFAULT NULL,
    status binary DEFAULT 1,
    updated datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    created datetime DEFAULT current_timestamp()
);

INSERT INTO customer (name, gender, birth, cccd, address, phone, email, facebook) VALUES
    ('Nguyễn Văn A', 'Male', '1990-05-15', '123456789', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123456789', 'nguyenvana@example.com', 'https://www.facebook.com/nguyenvana'),
    ('Trần Thị B', 'Female', '1985-09-20', '987654321', '456 Đường Nguyễn Huệ, Quận 3, Thành phố Hồ Chí Minh', '0987654321', 'tranthib@example.com', 'https://www.facebook.com/tranthib'),
    ('Phạm Văn C', 'Male', '1982-03-10', '456123789', '789 Đường Lê Duẩn, Quận 5, Thành phố Hồ Chí Minh', '0456123789', 'phamvanc@example.com', 'https://www.facebook.com/phamvanc'),
    ('Lê Thị D', 'Female', '1995-07-25', '987654321', '456 Đường Nguyễn Huệ, Quận 3, Thành phố Hồ Chí Minh', '0987654321', 'lethid@example.com', 'https://www.facebook.com/lethid'),
    ('Hoàng Văn E', 'Male', '1988-12-02', '123456789', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123456789', 'hoangvane@example.com', 'https://www.facebook.com/hoangvane'),
    ('Mai Thị F', 'Female', '1993-06-30', '456123789', '789 Đường Lê Duẩn, Quận 5, Thành phố Hồ Chí Minh', '0456123789', 'maithif@example.com', 'https://www.facebook.com/maithif'),
    ('Trương Văn G', 'Male', '1980-08-18', '123456789', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123456789', 'truongvang@example.com', 'https://www.facebook.com/truongvang'),
    ('Phan Thị H', 'Female', '1987-04-05', '987654321', '456 Đường Nguyễn Huệ, Quận 3, Thành phố Hồ Chí Minh', '0987654321', 'phanthih@example.com', 'https://www.facebook.com/phanthih'),
    ('Nguyễn Văn I', 'Male', '1991-11-11', '456123789', '789 Đường Lê Duẩn, Quận 5, Thành phố Hồ Chí Minh', '0456123789', 'nguyenvani@example.com', 'https://www.facebook.com/nguyenvani'),
    ('Lê Thị K', 'Female', '1984-02-28', '123456789', '123 Đường Lê Lợi, Quận 1, Thành phố Hồ Chí Minh', '0123456789', 'lethik@example.com', 'https://www.facebook.com/lethik');

-- ------------------------------------------------------------------------------------------
CREATE TABLE category(
    cat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    label_name VARCHAR(255) NOT NULL,
    updated datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    created datetime DEFAULT current_timestamp()
);

CREATE TABLE group_category(
    grp_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_name VARCHAR(255) NOT NULL,
    updated datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    created datetime DEFAULT current_timestamp()
);

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
    ('Áo thun'),
    ('Áo sơ mi'),
    ('Quần dài'),
    ('Quần short'),
    ('Điện thoại di động'),
    ('Máy tính xách tay'),
    ('Tủ lạnh'),
    ('Máy giặt'),
    ('Nước ngọt'),
    ('Nước suối');

-- Thêm dữ liệu cho bảng group_category
INSERT INTO group_category (group_name) VALUES
    ('Quần áo'),
    ('Thiết bị điện tử'),
    ('Đồ gia dụng'),
    ('Thức uống');

-- Bây giờ chúng ta sẽ ánh xạ (mapping) các danh mục vào nhóm tương ứng
-- Ví dụ: các danh mục liên quan đến quần áo sẽ được ánh xạ vào nhóm "Quần áo"
INSERT INTO rel_grp_cat (grp_id, cat_id) VALUES
    (1, 1), -- Áo thun -- do nha
    (1, 2), -- Áo sơ mi
    (1, 3), -- Quần dài
    (1, 4), -- Quần short

    (2, 5), -- Điện thoại di động
    (2, 6), -- Máy tính xách tay

    (3, 7), -- Tủ lạnh
    (3, 8), -- Máy giặt

    (4, 9), -- Nước ngọt
    (4, 10); -- Nước suối

INSERT INTO rel_cus_cat (cut_id, cat_id) VALUES
    (1, 1), -- Nguyễn Văn A quan tâm đến Áo thun
    (1, 2), -- Nguyễn Văn A quan tâm đến Áo sơ mi
    (2, 3), -- Trần Thị B quan tâm đến Quần dài
    (3, 7), -- Phạm Văn C quan tâm đến Tủ lạnh
    (4, 5), -- Lê Thị D quan tâm đến Điện thoại di động
    (5, 8), -- Hoàng Văn E quan tâm đến Máy giặt
    (6, 9), -- Mai Thị F quan tâm đến Nước ngọt
    (7, 3), -- Trương Văn G quan tâm đến Quần dài
    (8, 2), -- Phan Thị H quan tâm đến Áo sơ mi
    (9, 6); -- Nguyễn Văn I quan tâm đến Máy tính xách tay

-- foreign key
ALTER TABLE rel_cus_cat
    ADD CONSTRAINT fk_cus_cat_customer FOREIGN KEY (cut_id) REFERENCES customer(cus_id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT fk_cus_cat_category FOREIGN KEY (cat_id) REFERENCES category(cat_id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE rel_grp_cat
    ADD CONSTRAINT fk_grp_cat_group_category FOREIGN KEY (grp_id) REFERENCES group_category(grp_id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT fk_grp_cat_category FOREIGN KEY (cat_id) REFERENCES category(cat_id) ON DELETE CASCADE ON UPDATE CASCADE;
