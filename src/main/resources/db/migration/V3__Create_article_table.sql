CREATE TABLE article
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(128) NOT NULL,
    content TEXT,
    create_time BIGINT,
    modified_time BIGINT,
    author_id INT,
    comment_count INT DEFAULT 0,
    view_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    tag VARCHAR(256)
);