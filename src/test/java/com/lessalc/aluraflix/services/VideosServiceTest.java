package com.lessalc.aluraflix.services;

import com.lessalc.aluraflix.dto.VideoForm;
import com.lessalc.aluraflix.dto.VideoUpdate;
import com.lessalc.aluraflix.entities.Videos;
import com.lessalc.aluraflix.repositories.CategoriaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.*;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideosServiceTest {

    @Autowired
    VideosService service;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Test
    public void testaUpdateQuandoTodosItensSaoAtualizados(){
        VideoUpdate newObj = new VideoUpdate();
        newObj.setTitulo("VideoTeste-Atualizado");
        newObj.setDescricao("DescricaoTeste-Atualizado");
        newObj.setUrl("UrlTeste-Atualizado");

        Videos videoUpdated = service.update(1L, newObj);

        Videos videoId1 = service.findById(1L);

        assertEquals(newObj.getTitulo(), videoUpdated.getTitulo());
        assertEquals(newObj.getDescricao(), videoUpdated.getDescricao());
        assertEquals(newObj.getUrl(), videoUpdated.getUrl());
        assertEquals("UrlTeste-Atualizado", videoUpdated.getUrl());
        assertEquals(1L, videoUpdated.getId());

        assertEquals(videoId1, videoUpdated);
    }

    @Test
    public void testaUpdateQuandoAlgunsItensSaoAtualizados(){

        VideoUpdate newObj = new VideoUpdate();
        newObj.setTitulo("VideoTeste-Atualizado");

        Videos videoUpdated = service.update(1L, newObj);
        Videos videoId1 = service.findById(1L);

        assertEquals(newObj.getTitulo(), videoUpdated.getTitulo());
        assertEquals("Essa é uma descricao de um filme teste", videoUpdated.getDescricao());
        assertEquals("http://url.aas.test", videoUpdated.getUrl());
        assertEquals(videoId1, videoUpdated);
    }

    @Test
    public void testaUpdateQuandoAlgunsItensSaoAtualizadosEACategoriaTambem(){

        VideoUpdate newObj = new VideoUpdate();
        newObj.setTitulo("VideoTeste-Atualizado");
        newObj.setCategoria(3L);

        Videos videoUpdated = service.update(1L, newObj);
        Videos videoId1 = service.findById(1L);

        assertEquals(newObj.getTitulo(), videoUpdated.getTitulo());
        assertEquals("Essa é uma descricao de um filme teste", videoUpdated.getDescricao());
        assertEquals("http://url.aas.test", videoUpdated.getUrl());
        assertEquals(videoId1, videoUpdated);
        assertEquals(categoriaRepository.findById(3L).get(), videoUpdated.getCategoriaId());
    }

    @Test
    public void testaUpdateQuandoAlgunsItensSaoAtualizadosEACategoriaAtualizaComInexistente(){

        VideoUpdate newObj = new VideoUpdate();
        newObj.setTitulo("VideoTeste-Atualizado");
        newObj.setCategoria(300L);

        Videos videoUpdated = service.update(1L, newObj);
        Videos videoId1 = service.findById(1L);

        assertEquals(newObj.getTitulo(), videoUpdated.getTitulo());
        assertEquals("Essa é uma descricao de um filme teste", videoUpdated.getDescricao());
        assertEquals("http://url.aas.test", videoUpdated.getUrl());
        assertEquals(videoId1, videoUpdated);
    }

    @Test
    public void VerificaInsertDeProdutoCorretamente(){
        VideoForm form = new VideoForm("Titulo do Video", "Descricao do Video", "url.aas.teste", 2L);
        Videos videoSalvo = service.insert(form);

        assertEquals(videoSalvo.getCategoriaId().getId(), 2L);
        assertEquals("Titulo do Video", videoSalvo.getTitulo());
        assertEquals("Descricao do Video", videoSalvo.getDescricao());
        assertEquals("url.aas.teste", videoSalvo.getUrl());
    }

    @Test
    public void verificaInsertDeProdutoQuandoNaoEhInformadoCategoria(){
        VideoForm form = new VideoForm("Titulo do Video", "Descricao do Video", "url.aas.teste");
        Videos videoSalvo = service.insert(form);

        assertEquals(videoSalvo.getCategoriaId().getId(), 1L);
        assertEquals("Titulo do Video", videoSalvo.getTitulo());
        assertEquals("Descricao do Video", videoSalvo.getDescricao());
        assertEquals("url.aas.teste", videoSalvo.getUrl());
    }

    @Test
    public void verificaInsertDeProdutoQuandoCategoriaEhInvalida(){
        VideoForm form = new VideoForm("Titulo do Video", "Descricao do Video", "url.aas.teste", 1000L);
        Videos videoSalvo = service.insert(form);

        assertEquals(videoSalvo.getCategoriaId().getId(), 1L);
        assertEquals("Titulo do Video", videoSalvo.getTitulo());
        assertEquals("Descricao do Video", videoSalvo.getDescricao());
        assertEquals("url.aas.teste", videoSalvo.getUrl());
    }


}