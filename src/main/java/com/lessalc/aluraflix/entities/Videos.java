package com.lessalc.aluraflix.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Videos implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String descricao;
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoriaId;
	
	public Videos() {
	}

	public Videos(Long id, String titulo, String descricao, String url) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.url = url;
	}

	public Videos(String titulo, String descricao, String url, Categoria categoria) {
		super();
		this.titulo = titulo;
		this.descricao = descricao;
		this.url = url;
		this.categoriaId = categoria;
	}

	public Videos(Long id, String titulo, String descricao, String url, Categoria categoriaId) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.url = url;
		this.categoriaId = categoriaId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Categoria getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Categoria categoriaId) {
		this.categoriaId = categoriaId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Videos)) return false;
		Videos videos = (Videos) o;
		return Objects.equals(getId(), videos.getId()) && Objects.equals(getTitulo(), videos.getTitulo()) && Objects.equals(getDescricao(), videos.getDescricao()) && Objects.equals(getUrl(), videos.getUrl()) && Objects.equals(getCategoriaId(), videos.getCategoriaId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getTitulo(), getDescricao(), getUrl(), getCategoriaId());
	}
}
