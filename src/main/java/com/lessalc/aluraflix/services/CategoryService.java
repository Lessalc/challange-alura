package com.lessalc.aluraflix.services;

import java.util.List;
import java.util.Optional;

import com.lessalc.aluraflix.dto.CategoriaUpdate;
import com.lessalc.aluraflix.services.exception.ResourceNotFoundException;
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
		Categoria categoria = findCategoria(id);

		return categoria.getVideos();
	}

	public Categoria findCategoria(Long id) {
		Optional<Categoria> categoria = repository.findById(id);

		return categoria.orElseThrow(() -> new ResourceNotFoundException());
	}

    public List<Categoria> findAll() {
		return repository.findAll();
    }

    public Categoria insert(Categoria categoria) {
		return repository.save(categoria);
    }

	public Categoria update(Long id, CategoriaUpdate obj) {
		Categoria cat = findCategoria(id);
		updateData(cat, obj);
		return repository.save(cat);
	}

	private void updateData(Categoria oldObj, CategoriaUpdate newObj) {
		if(newObj.getCategoria() != null)
			if(newObj.getCategoria().length() > 0)
				oldObj.setCategoria(newObj.getCategoria());
		if(newObj.getCor() != null)
			if(newObj.getCor().length() > 0)
				oldObj.setCor(newObj.getCor());
	}

	public void deletarCategoria(Long id){

		List<Videos> videos = findVideos(id);
		Categoria categoria = findCategoria(1L);
		videos.forEach(video -> video.setCategoriaId(categoria));
		Categoria obj = findCategoria(id);
		repository.delete(obj);
	}
}
