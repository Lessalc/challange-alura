package com.lessalc.aluraflix.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VideoForm {

    @NotNull
    @NotEmpty
    private String titulo;
    @NotNull @NotEmpty @Size(min=10)
    private String descricao;
    @NotNull @NotEmpty @Size(min=10)
    private String url;

    private Long categoria;

    public VideoForm() {
    }

    public VideoForm(String titulo, String descricao, String url, Long categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
        this.categoria = categoria;
    }

    public VideoForm(String titulo, String descricao, String url) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
        this.categoria = null;
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

    public Long getCategoria() {
        return categoria;
    }

    public void setCategoria(Long categoria) {
        this.categoria = categoria;
    }
}
