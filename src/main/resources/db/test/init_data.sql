DELETE FROM scheduler;
ALTER TABLE scheduler AUTO_INCREMENT = 1;
INSERT INTO scheduler (id, todo, writer, password, created_at, updated_at) VALUES (1, '청소', '고길동', '2222', '2025-05-01', '2025-05-03');
INSERT INTO scheduler (id, todo, writer, password, created_at, updated_at) VALUES (2, '숙제', '김유신', '3333', '2025-05-02', '2025-05-04');
INSERT INTO scheduler (id, todo, writer, password, created_at, updated_at) VALUES (3, '빨래', '강감찬', '4444', '2025-05-03', '2025-05-05');