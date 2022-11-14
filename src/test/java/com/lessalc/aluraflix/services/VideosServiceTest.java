package com.lessalc.aluraflix.services;

import com.lessalc.aluraflix.dto.VideoForm;
import com.lessalc.aluraflix.dto.VideoUpdate;
import com.lessalc.aluraflix.entities.Categoria;
import com.lessalc.aluraflix.entities.Videos;
import com.lessalc.aluraflix.repositories.CategoriaRepository;
import com.lessalc.aluraflix.repositories.VideosRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

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

        Videos oldObj = new Videos(null, "VideoTeste-Inicial", "DescricaoTeste-Inicial", "UrlTeste-Inicial");
        VideoUpdate newObj = new VideoUpdate();
        newObj.setTitulo("VideoTeste-Atualizado");
        newObj.setDescricao("DescricaoTeste-Atualizado");
        newObj.setUrl("UrlTeste-Atualizado");

        service.updateData(oldObj, newObj);

        assertEquals(oldObj.getTitulo(), newObj.getTitulo());
        assertEquals(oldObj.getDescricao(), newObj.getDescricao());
        assertEquals(oldObj.getUrl(), newObj.getUrl());
        assertEquals(oldObj.getUrl(), "UrlTeste-Atualizado");
    }

    @Test
    public void testaUpdateQuandoAlgunsItensSaoAtualizados(){

        Videos oldObj = new Videos(null, "VideoTeste-Inicial", "DescricaoTeste-Inicial", "UrlTeste-Inicial");
        VideoUpdate newObj = new VideoUpdate();
        newObj.setTitulo("VideoTeste-Atualizado");

        service.updateData(oldObj, newObj);

        assertEquals(oldObj.getTitulo(), newObj.getTitulo());
        assertEquals(oldObj.getDescricao(), "DescricaoTeste-Inicial");
        assertEquals(oldObj.getUrl(), "UrlTeste-Inicial");
    }

    @Test
    public void testaUpdateQuandoAlgunsItensSaoAtualizadosEACategoriaTambem(){

        Videos oldObj = new Videos(null, "VideoTeste-Inicial", "DescricaoTeste-Inicial", "UrlTeste-Inicial");
        VideoUpdate newObj = new VideoUpdate();
        newObj.setTitulo("VideoTeste-Atualizado");
        newObj.setCategoria(3L);

        service.updateData(oldObj, newObj);

        Optional<Categoria> byId = categoriaRepository.findById(3L);

        assertEquals(oldObj.getTitulo(), newObj.getTitulo());
        assertEquals(oldObj.getDescricao(), "DescricaoTeste-Inicial");
        assertEquals(oldObj.getUrl(), "UrlTeste-Inicial");
        if(byId.isPresent())
            assertEquals(oldObj.getCategoriaId(), byId.get());
    }

    @Test
    public void verificaVideoRetornadoAPartirDeFormulario(){
        VideoForm videoForm = new VideoForm();
        videoForm.setTitulo("Teste Video Form");
        videoForm.setDescricao("Descricao Teste Video Form");
        videoForm.setUrl("url.teste.video.form");
        videoForm.setCategoria(2L);

        Videos videos = service.retornaObjetoVideo(videoForm);

        if(videoForm.getCategoria() != null){
            Optional<Categoria> byId = categoriaRepository.findById(2L);
            if(byId.isPresent())
                assertEquals(videos.getCategoriaId(), byId.get());
        }

        assertEquals(videos.getTitulo(), "Teste Video Form");
        assertEquals(videos.getDescricao(), "Descricao Teste Video Form");
        assertEquals(videos.getUrl(), "url.teste.video.form");

    }

    @Test
    public void verificaVideoRetornadoAPartirDeFormularioQuandoCategoriaEhNula(){
        VideoForm videoForm = new VideoForm();
        videoForm.setTitulo("Teste Video Form");
        videoForm.setDescricao("Descricao Teste Video Form");
        videoForm.setUrl("url.teste.video.form");

        Videos videos = service.retornaObjetoVideo(videoForm);

        Optional<Categoria> byId = categoriaRepository.findById(1L);

        assertEquals(videos.getTitulo(), "Teste Video Form");
        assertEquals(videos.getDescricao(), "Descricao Teste Video Form");
        assertEquals(videos.getUrl(), "url.teste.video.form");
        if(byId.isPresent())
            assertEquals(videos.getCategoriaId(), byId.get());
    }

    @Test
    public void verificaVideoRetornadoAPartirDeFormularioQuandoCategoriaEhInvalida(){
        VideoForm videoForm = new VideoForm();
        videoForm.setTitulo("Teste Video Form");
        videoForm.setDescricao("Descricao Teste Video Form");
        videoForm.setUrl("url.teste.video.form");
        videoForm.setCategoria(100L);

        Videos videos = service.retornaObjetoVideo(videoForm);

        Optional<Categoria> byId = categoriaRepository.findById(1L);

        assertEquals(videos.getTitulo(), "Teste Video Form");
        assertEquals(videos.getDescricao(), "Descricao Teste Video Form");
        assertEquals(videos.getUrl(), "url.teste.video.form");
        if(byId.isPresent())
            assertEquals(videos.getCategoriaId(), byId.get());
    }

}