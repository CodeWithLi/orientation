-- 创建一个orientation迎新数据库
CREATE DATABASE orientation;

--  使用数据库
USE orientation;

-- 创建一个后台管理员用户表
CREATE TABLE admins
(
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone    VARCHAR(255) NOT NULL
);
COMMENT ON TABLE Roles IS '后台管理员用户表';

-- 创建角色表
CREATE TABLE roles
(
    role_id     INT PRIMARY KEY AUTO_INCREMENT,
    role_name   VARCHAR(255) NOT NULL
);
COMMENT ON TABLE Roles IS '后台管理员角色表';

-- 创建管理员-角色关联表
CREATE TABLE admin_roles
(
    admin_role_id INT PRIMARY KEY AUTO_INCREMENT,
    admin_id      INT,
    role_id       INT,
    FOREIGN KEY (admin_id) REFERENCES admins (admin_id),
    FOREIGN KEY (role_id) REFERENCES Roles (role_id)
);
COMMENT ON TABLE AdminRoles IS '后台管理员-角色关联表';
