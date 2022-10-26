package com.lessalc.aluraflix.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lessalc.aluraflix.entities.Videos;
import com.lessalc.aluraflix.repositories.VideosRepository;
import com.lessalc.aluraflix.services.exception.ResourceNotFoundException;

@Service
public class VideosService {

	@Autowired
	VideosRepository repository;
	
	public List<Videos> findAll(){
		return repository.findAll();
	}
	
	public Videos findById(Long id) {
		Optional<Videos> video = repository.findById(id);
		
		return video.orElseThrow(() -> new ResourceNotFoundException());
	}
}
