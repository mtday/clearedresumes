
INSERT INTO users (id, login, email, password, enabled) VALUES
('1', 'test', 'test@decojo.com', '$2a$10$BVkDFSdtJbj0oURvZWDMBur.NVvoafMuycwiE2RbIoIn9pv4.lh32', TRUE);

INSERT INTO authorities (user_id, authority) VALUES
('1', 'ADMIN'),
('1', 'USER');

