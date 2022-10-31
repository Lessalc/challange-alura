package com.lessalc.aluraflix.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.lessalc.aluraflix.entities.Videos;

public class VideoDto {

	@NotNull @NotEmpty @Size(min=10)
	private String titulo;

	@NotNull @NotEmpty @Size(min=10)
	private String url;
	
	public VideoDto(Videos video) {
		this.titulo = video.getTitulo();
		this.url = video.getUrl();
	}
	
	public String getTitulo() {
		return titulo;
	}

	public String getUrl() {
		return url;
	}
	
	
}
