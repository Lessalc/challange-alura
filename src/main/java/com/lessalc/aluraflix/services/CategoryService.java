package com.lessalc.aluraflix.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lessalc.aluraflix.entities.Categoria;
import com.lessalc.aluraflix.entities.Videos;
import com.lessalc.aluraflix.repositories.CategoriaRepository;

@Service
public class CategoryService {

	@Autowired
	CategoriaRepository repository;
	
	public List<Videos> findVideos(Long id) {

		Categoria categoria = repository.findById(id).get();
		return categoria.getVideos();
	}

	public Categoria findCategoria(Long id) {
		return repository.findById(id).get();
	}

}
