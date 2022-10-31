package com.lessalc.aluraflix.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lessalc.aluraflix.entities.Categoria;
import com.lessalc.aluraflix.entities.Videos;
import com.lessalc.aluraflix.repositories.CategoriaRepository;
import com.lessalc.aluraflix.repositories.VideosRepository;
import com.lessalc.aluraflix.services.exception.ResourceNotFoundException;

@Service
public class VideosService {

	@Autowired
	VideosRepository repository;
	
	@Autowired
	CategoriaRepository CategoriaRepository;
	
	public List<Videos> findAll(){
		return repository.findAll();
	}
	
	public Videos findById(Long id) {
		Optional<Videos> video = repository.findById(id);
		
		return video.orElseThrow(() -> new ResourceNotFoundException());
	}

	public Videos insert(Videos obj) {
		if (obj.getCategoriaId() == null) {
			Categoria categoria = CategoriaRepository.findById(1L).get();
			obj.setCategoriaId(categoria);
		}
		return repository.save(obj);
	}

	public Videos update(Long id, Videos obj) {
		Videos video = findById(id);
		updateData(video, obj);
		return repository.save(video);
	}
	
	private void updateData(Videos oldObj, Videos newObj) {
		oldObj.setTitulo(newObj.getTitulo());
		oldObj.setDescricao(newObj.getDescricao());
		oldObj.setUrl(newObj.getUrl());
	}

	public void delete(Long id) {
		Videos obj = findById(id);
		repository.delete(obj);
	}

	public List<Videos> findByTitulo(String titulo) {
		
		return repository.findByTituloContainingIgnoreCase(titulo);
	}
	
	
	
}
