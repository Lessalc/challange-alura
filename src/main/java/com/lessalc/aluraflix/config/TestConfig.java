package com.lessalc.aluraflix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.lessalc.aluraflix.entities.Videos;
import com.lessalc.aluraflix.repositories.VideosRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private VideosRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
	
		Videos videoTest = new Videos(null, "Filme Teste", "Essa é uma descricao de um filme teste", "http://url.aas.test");
		
		repository.save(videoTest);
		
	}

}
