package com.lessalc.aluraflix.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lessalc.aluraflix.dto.VideoDto;
import com.lessalc.aluraflix.entities.Categoria;
import com.lessalc.aluraflix.entities.Videos;
import com.lessalc.aluraflix.services.CategoryService;
import com.lessalc.aluraflix.services.VideosService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

	@Autowired
	private CategoryService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> findCategory(@PathVariable Long id){
		Categoria categoria = service.findCategoria(id);
		
		return ResponseEntity.ok().body(categoria);
	}
	
	@GetMapping(value = "/{id}/videos")
	public ResponseEntity<List<VideoDto>> findByCategory(@PathVariable Long id){
		List<Videos> videos = service.findVideos(id);
		
		return ResponseEntity.ok().body(videos.stream().map(VideoDto::new).collect(Collectors.toList()));
	}
}
