INSERT INTO `member` (name, email, password, created_at) VALUES ('admin', 'admin@example.com', '1234', now());

INSERT INTO `session` (access_token, member_id) VALUES ('6392da07-d89c-4c26-936a-53590471bdbe', 1);
