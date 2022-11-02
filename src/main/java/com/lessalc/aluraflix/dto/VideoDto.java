package com.lessalc.aluraflix.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lessalc.aluraflix.entities.Videos;

public class VideoDto {

	private Long id;
	private Long categoriaId;
	private String titulo;
	private String descricao;
	private String url;
	
	public VideoDto(Videos video) {
		this.id = video.getId();
		this.categoriaId = video.getCategoriaId().getId();
		this.titulo = video.getTitulo();
		this.descricao = video.getDescricao();
		this.url = video.getUrl();
	}

	public Long getId() {
		return id;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getUrl() {
		return url;
	}
	
		
	
}
