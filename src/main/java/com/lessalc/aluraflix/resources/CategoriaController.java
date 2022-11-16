package com.lessalc.aluraflix.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.lessalc.aluraflix.dto.CategoriaUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lessalc.aluraflix.dto.VideoDto;
import com.lessalc.aluraflix.entities.Categoria;
import com.lessalc.aluraflix.entities.Videos;
import com.lessalc.aluraflix.services.CategoryService;
import com.lessalc.aluraflix.services.VideosService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

	@Autowired
	private CategoryService service;

	@GetMapping
	public ResponseEntity<List<Categoria>> findAllCategory(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
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

	@PostMapping
	public ResponseEntity<Categoria> createCategory(@RequestBody @Valid Categoria categoria){
		categoria = service.insert(categoria);
		// Criando um objeto URI para que possamos ter a localização no nosso header de onde o objeto foi criado
		URI uri =  ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).body(categoria);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Categoria> atualizaCategoria(@RequestBody CategoriaUpdate categoria, @PathVariable Long id){
		Categoria obj = service.update(id, categoria);
		return ResponseEntity.ok().body(obj);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Categoria> deletaCategoria(@PathVariable Long id){
		if(id == 1L)
			return ResponseEntity.badRequest().build();
		service.deletarCategoria(id);
		return ResponseEntity.noContent().build();
	}


}
