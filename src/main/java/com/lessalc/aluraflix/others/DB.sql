CREATE DATABASE aluraflixdb;

USE aluraflixdb;

CREATE TABLE videos(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	titulo VARCHAR(50),
	descricao VARCHAR(200),
	url VARCHAR(200)
);


INSERT INTO videos (titulo, descricao, url) VALUES ('Filme Teste', 'Essa é uma descricao de um filme teste', 'http://url.aas.test');

SELECT * FROM videos;