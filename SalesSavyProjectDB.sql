/* ========================================= */
/* creating DB                               */
/* ========================================= */
use salessavvy;

/* ========================================= */
/* creating tables for roles, users, tokens  */
/* ========================================= */

/* Table: Roles */
/* Description: Defines the roles available for users in the system */
CREATE TABLE Roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY, 
    role_name VARCHAR(50) NOT NULL UNIQUE CHECK (role_name IN ('Admin', 'Customer'))
);

/* Table: users */
/* Description: Stores user information and credentials */
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'CUSTOMER') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

/* Table: jwt_tokens */
/* Description: Stores JSON Web Tokens for authenticated users */
CREATE TABLE jwt_tokens (
    token_id INT AUTO_INCREMENT PRIMARY KEY, 
    user_id INT NOT NULL,                         
    token TEXT NOT NULL,
    expires_at TIMESTAMP NOT NULL,                
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

/* Table: otprequests */
/* Description: Manages OTP requests for email verification */
CREATE TABLE otprequests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    otp VARCHAR(6) NOT NULL,
    created_at DATETIME not null
);

/* ========================================= */
/* Product and Category Management           */
/* ========================================= */

/* Create the `categories` table */
/* Description: Stores different categories for products */
CREATE TABLE categories (
    category_id INT NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (category_id),
    UNIQUE KEY (category_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* Create the `products` table */
/* Description: Stores the main product details, linked to a category */
CREATE TABLE products (
    product_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    category_id INT DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (product_id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* Create the `productimages` table */
/* Description: Stores the image URLs associated with products */
CREATE TABLE productimages (
    image_id INT NOT NULL AUTO_INCREMENT,
    product_id INT NOT NULL,
    image_url TEXT NOT NULL,
    PRIMARY KEY (image_id),
    FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* ========================================= */
/* Data Insertion                            */
/* ========================================= */

/* inserting categories into categories table */
/* Description: Pre-populating some default categories */
INSERT INTO categories (category_id, category_name)
VALUES 
    (1, 'Shirts'),
    (2, 'Pants'),
    (3, 'Accessories'),
    (4, 'Mobiles'),
    (5, 'Mobile Accessories');

/* inserting products into products table */
/* Description: Pre-populating a default product */
INSERT INTO `products` (product_id, name, description, price, stock, category_id, created_at, updated_at) 
VALUES 
    (1, 'Shirt1', 'Stylish Shirt1', 499.99, 100, 1, '2025-01-01 17:41:26', '2025-01-01 17:41:26');

/* inserting productimages into product image table */
/* Description: Pre-populating a default image for the product */
INSERT INTO `productimages` (image_id, product_id, image_url) 
VALUES 
    (1, 1, 'url');

/* ========================================= */
/* Shopping Cart                             */
/* ========================================= */

/* caritems */
/* Description: Stores products added to the cart by users */
CREATE TABLE `cart_items` (
    `id` int NOT NULL AUTO_INCREMENT,
    `user_id` int NOT NULL,
    `product_id` int NOT NULL,
    `quantity` int NOT NULL,
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `product_id` (`product_id`),
    CONSTRAINT `cart_items_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `cart_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* ========================================= */
/* Orders & Payment Gateway                  */
/* ========================================= */

/* payment gateway (Orders table) */
/* Description: Stores top-level order details and transaction status */
CREATE TABLE `orders` (
    `order_id` varchar(255) NOT NULL,
    `user_id` int NOT NULL,
    `total_amount` decimal(10,2) NOT NULL,
    `status` enum('PENDING', 'SUCCESS', 'FAILED') DEFAULT 'PENDING',
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`order_id`),
    KEY `user_id`(`user_id`),
    CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/* Table: order_items */
/* Description: Stores individual products for a given order */
CREATE TABLE `order_items` (
    `id` int NOT NULL AUTO_INCREMENT,
    `order_id` varchar(255) NOT NULL,
    `product_id` int NOT NULL,
    `quantity` int NOT NULL,
    `price_per_unit` decimal(10,2) NOT NULL,
    `total_price` decimal(10,2) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `order_id` (`order_id`),
    KEY `product_id` (`product_id`),
    CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
    CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/* END */