package com.lessalc.aluraflix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lessalc.aluraflix.entities.Categoria;
import com.lessalc.aluraflix.entities.Videos;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
