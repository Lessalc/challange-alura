package com.lessalc.aluraflix.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lessalc.aluraflix.entities.Videos;
import com.lessalc.aluraflix.services.VideosService;

@RestController
@RequestMapping(value = "/videos")
public class VideosController {

	@Autowired
	private VideosService service;
	
	@GetMapping
	public ResponseEntity<List<Videos>> findAll(){
		
		List<Videos> videos = service.findAll();
		
		return ResponseEntity.ok().body(videos);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Videos> findById(@PathVariable Long id){
		
		Videos video = service.findById(id);
		return ResponseEntity.ok().body(video);
	}
	
}
