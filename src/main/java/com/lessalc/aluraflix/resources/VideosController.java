package com.lessalc.aluraflix.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lessalc.aluraflix.dto.VideoDto;
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
	public ResponseEntity<VideoDto> findById(@PathVariable Long id){
		
		Videos video = service.findById(id);
		VideoDto videoDto = new VideoDto(video);
		return ResponseEntity.ok().body(videoDto);
	}
	
	@GetMapping(value = "/")
	public ResponseEntity<List<Videos>> findByTitulo(String titulo){
		
		List<Videos> videos = service.findByTitulo(titulo);
		return ResponseEntity.ok().body(videos);
	}
	
	@PostMapping	
	public ResponseEntity<Videos> insert(@RequestBody @Valid Videos obj){
		obj = service.insert(obj);
		// Criando um objeto URI para que possamos ter a localização no nosso header de onde o objeto foi criado
		URI uri =  ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Videos> update(@PathVariable Long id ,@RequestBody Videos obj){
		obj = service.update(id, obj);
		URI uri =  ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);	
	}
	
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.ok().body("Operação concluída com sucesso");
	}
			
	
}
