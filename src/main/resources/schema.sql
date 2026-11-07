CREATE DATABASE bookstore;

USE bookstore;

CREATE TABLE books (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       category VARCHAR(100),
                       price DECIMAL(10, 2)
);

INSERT INTO books (title, author, category, price) VALUES
('Atomic Habits', 'James Clear', 'Productivity', 16.99),
('The Power of Now', 'Eckhart Tolle', 'Mindfulness', 14.99),
('Think and Grow Rich', 'Napoleon Hill', 'Success', 12.99);