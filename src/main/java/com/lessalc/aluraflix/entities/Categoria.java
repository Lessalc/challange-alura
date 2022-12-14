package com.lessalc.aluraflix.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Categoria {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @NotEmpty
    private String categoria;
    @NotNull @NotEmpty
    private String cor;

    @OneToMany(mappedBy = "categoriaId")
    @JsonIgnore
    private List<Videos> videos;

    public Categoria() {
    }

    public Categoria(Long id, String categoria) {
        super();
        this.id = id;
        this.categoria = categoria;
    }

    public Categoria(Long id, String categoria, String cor) {
        this.id = id;
        this.categoria = categoria;
        this.cor = cor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public List<Videos> getVideos() {
        return videos;
    }

    public void setVideos(List<Videos> videos) {
        this.videos = videos;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        Categoria categoria1 = (Categoria) o;
        return Objects.equals(getId(), categoria1.getId()) && Objects.equals(getCategoria(), categoria1.getCategoria()) && Objects.equals(getCor(), categoria1.getCor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCategoria(), getCor());
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", categoria='" + categoria + '\'' +
                ", cor='" + cor + '\'' +
                ", videos=" + videos +
                '}';
    }
}
