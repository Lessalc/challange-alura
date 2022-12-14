package com.lessalc.aluraflix.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.lessalc.aluraflix.dto.VideoForm;
import com.lessalc.aluraflix.dto.VideoUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.lessalc.aluraflix.entities.Categoria;
import com.lessalc.aluraflix.entities.Videos;
import com.lessalc.aluraflix.repositories.CategoriaRepository;
import com.lessalc.aluraflix.repositories.VideosRepository;
import com.lessalc.aluraflix.services.exception.ResourceNotFoundException;

@Service
public class VideosService {

	private VideosRepository repository;
	private CategoriaRepository categoriaRepository;

	@Autowired
	public VideosService(VideosRepository repository, CategoriaRepository categoriaRepository) {
		this.repository = repository;
		this.categoriaRepository = categoriaRepository;
	}

	public List<Videos> findAll(){
		return repository.findAll();
	}
	
	public Videos findById(Long id) {
		Optional<Videos> video = repository.findById(id);
		
		return video.orElseThrow(() -> new ResourceNotFoundException());
	}

	public Videos insert(VideoForm obj) {
		Videos videos = retornaObjetoVideo(obj);

		return repository.save(videos);
	}

	private Videos retornaObjetoVideo(VideoForm obj){
		if(obj.getCategoria() == null){
			Optional<Categoria> optionalCategoria = categoriaRepository.findById(1L);
			return new Videos(obj.getTitulo(), obj.getDescricao(), obj.getUrl(), optionalCategoria.get());
		} else{
			Optional<Categoria> optionalCategoria = categoriaRepository.findById(obj.getCategoria());
			if(optionalCategoria.isPresent()){
				return new Videos(obj.getTitulo(), obj.getDescricao(), obj.getUrl(), optionalCategoria.get());
			} else{
				optionalCategoria = categoriaRepository.findById(1L);
				return new Videos(obj.getTitulo(), obj.getDescricao(), obj.getUrl(), optionalCategoria.get());
			}
		}
	}

	public Videos update(Long id, VideoUpdate obj) {
		Videos video = findById(id);
		updateData(video, obj);
		return repository.save(video);
	}
	
	private void updateData(Videos oldObj, VideoUpdate newObj) {
		if(newObj.getTitulo() != null)
			if(newObj.getTitulo().length() > 0)
				oldObj.setTitulo(newObj.getTitulo());
		if(newObj.getDescricao() != null)
			if(newObj.getDescricao().length() >= 10)
				oldObj.setDescricao(newObj.getDescricao());
		if(newObj.getUrl() != null)
			if(newObj.getUrl().length() >= 10)
				oldObj.setUrl(newObj.getUrl());

		if(newObj.getCategoria() != null){
			Optional<Categoria> optionalCategoria = categoriaRepository.findById(newObj.getCategoria());
			optionalCategoria.ifPresent(oldObj::setCategoriaId);
		}
	}

	public void delete(Long id) {
		Videos obj = findById(id);
		repository.delete(obj);
	}

	public List<Videos> findByTitulo(String titulo) {
		
		return repository.findByTituloContainingIgnoreCase(titulo);
	}
}
