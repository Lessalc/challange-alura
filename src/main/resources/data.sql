INSERT INTO categoria (categoria, cor) VALUES ('Livre', 'Cinza');
INSERT INTO categoria (categoria, cor) VALUES ('Programação', 'Verde');
INSERT INTO categoria (categoria, cor) VALUES ('Front-end', 'Azul');
INSERT INTO categoria (categoria, cor) VALUES ('Data Science', 'Verde-claro');

INSERT INTO videos (titulo, descricao, url, categoria_id) VALUES ('Filme Teste', 'Essa é uma descricao de um filme teste', 'http://url.aas.test', 1);
INSERT INTO videos (titulo, descricao, url, categoria_id) VALUES ('Filme Teste 2', 'Descricao Teste 2', 'http://url.aas.test2', 3);

INSERT INTO USUARIO(nome, email, senha) VALUES('Admin', 'admin@email.com', '$2a$10$OBor7HYm5YQfX4qVoQgmXec1dGCskaxWTBW4S6QKm3MBjfBWCl0gq');