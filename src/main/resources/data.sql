INSERT INTO member_entity (id, username, password, role) VALUES (1001, 'admin', '$2a$10$3DVujfFeizZfj9eLNOE5Ju6D45wWTU/CyY/m5VSFmGsYCZHjBrtU.', 'ADMIN');

INSERT INTO diary (id, title, content, member_id, created_at)
VALUES
    (1001, 'First Diary Entry', 'This is the first diary content.', 1001, NOW()),
    (1002, 'Second Diary Entry', 'This is the second diary content.', 1001, NOW()),
    (1003, 'Third Diary Entry', 'This is the third diary content.', 1001, NOW());