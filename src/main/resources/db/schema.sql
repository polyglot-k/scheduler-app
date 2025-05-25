CREATE TABLE IF NOT EXISTS scheduler (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         todo VARCHAR(200) NOT NULL,
                                         writer VARCHAR(100) NOT NULL,
                                         password VARCHAR(100) NOT NULL,
                                         created_at DATE NOT NULL,
                                         updated_at DATE NOT NULL
);
CREATE TABLE IF NOT EXISTS scheduler2 (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          todo VARCHAR(200) NOT NULL,
                                          created_at DATE NOT NULL,
                                          updated_at DATE NOT NULL,
                                          u_id bigint not null,
                                          foreign key (u_id) references users(id)
                                              on delete cascade
                                              on update cascade
);
create table if not exists users(
                                    id bigint auto_increment primary key,
                                    email varchar(100) not null,
                                    name VARCHAR(100) NOT NULL,
                                    password VARCHAR(100) NOT NULL,
                                    created_at DATE NOT NULL,
                                    updated_at DATE NOT NULL
);
