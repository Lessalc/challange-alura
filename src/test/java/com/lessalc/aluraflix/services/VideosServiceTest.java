package com.lessalc.aluraflix.services;

import com.lessalc.aluraflix.dto.VideoForm;
import com.lessalc.aluraflix.dto.VideoUpdate;
import com.lessalc.aluraflix.entities.Categoria;
import com.lessalc.aluraflix.entities.Videos;
import com.lessalc.aluraflix.repositories.CategoriaRepository;
import com.lessalc.aluraflix.repositories.VideosRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideosServiceTest {

    private VideosService service;
    @Mock
    private VideosRepository repository;
    @Mock
    private CategoriaRepository categoriaRepository;

    @Captor
    private ArgumentCaptor<Videos> captor;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(repository);
        MockitoAnnotations.openMocks(categoriaRepository);
        service = new VideosService(repository, categoriaRepository);
    }
    private List<Categoria> retornaCategorias(){
        return new ArrayList<>(Arrays.asList(new Categoria(1L, "Livre", "Cinza"),
                new Categoria(2L, "Programação", "Verde"),
                new Categoria(3L, "Front-end", "Azul"),
                new Categoria(4L, "Data Science", "Verde-claro")));
    }

    private  List<Videos> retornaVideos(){
        List<Categoria> categorias = retornaCategorias();
        return new ArrayList<>(Arrays.asList(
                new Videos(1L, "Video 1", "Descricao Video 1", "url.video1.test", categorias.get(0)),
                new Videos(1L, "Video 2", "Descricao Video 2", "url.video2.test", categorias.get(2)),
                new Videos(1L, "Video 3", "Descricao Video 3", "url.video3.test", categorias.get(0)),
                new Videos(1L, "Video 4", "Descricao Video 4", "url.video4.test", categorias.get(1))));
    }

    @Test
    public void testaInsertQuandoEnviadoUmaCategoriaCorretamente(){
        List<Categoria> categorias = retornaCategorias();
        VideoForm form = new VideoForm("Video Inserido", "Descricao de video Inserido",
                "url.videoInserido", 2L);
        Mockito.when(categoriaRepository.findById(2L)).thenReturn(Optional.ofNullable(categorias.get(1)));

        service.insert(form);
        Mockito.verify(repository).save(captor.capture());
        Videos video = captor.getValue();

        Assert.assertEquals("Video Inserido", video.getTitulo());
        Assert.assertEquals("Descricao de video Inserido", video.getDescricao());
        Assert.assertEquals("url.videoInserido", video.getUrl());
        Assert.assertEquals(categorias.get(1).getCategoria(), video.getCategoriaId().getCategoria());
        Mockito.verify(repository).save(video);
        Mockito.verify(categoriaRepository).findById(2L);
        Mockito.verify(categoriaRepository, Mockito.never()).findById(1L);
    }

    @Test
    public void testaInsertQuandoNaoEnviamosUmaCategoria(){
        List<Categoria> categorias = retornaCategorias();
        VideoForm form = new VideoForm("Video Inserido", "Descricao de video Inserido",
                "url.videoInserido");
        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.ofNullable(categorias.get(0)));

        service.insert(form);
        Mockito.verify(repository).save(captor.capture());
        Videos video = captor.getValue();

        Assert.assertEquals("Video Inserido", video.getTitulo());
        Assert.assertEquals("Descricao de video Inserido", video.getDescricao());
        Assert.assertEquals("url.videoInserido", video.getUrl());
        Assert.assertEquals(categorias.get(0).getCategoria(), video.getCategoriaId().getCategoria());
        Mockito.verify(repository).save(video);
        Mockito.verify(categoriaRepository).findById(1L);
    }

    @Test
    public void testaInsertQuandoEnviamosUmaCategoriaInvalida(){
        List<Categoria> categorias = retornaCategorias();
        VideoForm form = new VideoForm("Video Inserido", "Descricao de video Inserido",
                "url.videoInserido", 200L);
        Mockito.when(categoriaRepository.findById(200L)).thenReturn(Optional.empty());
        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.ofNullable(categorias.get(0)));

        service.insert(form);
        Mockito.verify(repository).save(captor.capture());
        Videos video = captor.getValue();

        Assert.assertEquals("Video Inserido", video.getTitulo());
        Assert.assertEquals("Descricao de video Inserido", video.getDescricao());
        Assert.assertEquals("url.videoInserido", video.getUrl());
        Assert.assertEquals(categorias.get(0).getCategoria(), video.getCategoriaId().getCategoria());
        Mockito.verify(repository).save(video);
        Mockito.verify(categoriaRepository).findById(200L);
        Mockito.verify(categoriaRepository).findById(1L);

    }

    @Test
    public void testaUpdateQuandoTodosItensSaoAtualizadosExcetoCategoria(){
        List<Videos> videos = retornaVideos();

        VideoUpdate newObj = new VideoUpdate("VideoTeste-Atualizado", "DescricaoTeste-Atualizado",
                "UrlTeste-Atualizado");

        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(videos.get(0)));

        service.update(1L, newObj);
        Mockito.verify(repository).save(captor.capture());
        Videos videoUpdated = captor.getValue();

        Assert.assertEquals("VideoTeste-Atualizado", videoUpdated.getTitulo());
        Assert.assertEquals("DescricaoTeste-Atualizado", videoUpdated.getDescricao());
        Assert.assertEquals("UrlTeste-Atualizado", videoUpdated.getUrl());
        Assert.assertEquals(videos.get(0).getCategoriaId().getCategoria(), videoUpdated.getCategoriaId().getCategoria());
        Mockito.verify(repository).save(videoUpdated);
        Mockito.verify(repository).findById(1L);
        Mockito.verify(categoriaRepository, Mockito.never()).findById(newObj.getCategoria());
    }

    @Test
    public void testaUpdateQuandoAlgunsItensSaoAtualizados(){
        VideoUpdate newObj = new VideoUpdate();
        newObj.setTitulo("VideoTeste-Atualizado");
        List<Videos> videos = retornaVideos();

        Mockito.when(repository.findById(2L)).thenReturn(Optional.ofNullable(videos.get(1)));

        service.update(2L, newObj);
        Mockito.verify(repository).save(captor.capture());
        Videos videoUpdated = captor.getValue();

        Assert.assertEquals("VideoTeste-Atualizado", videoUpdated.getTitulo());
        Assert.assertEquals(videos.get(1).getDescricao(), videoUpdated.getDescricao());
        Assert.assertEquals(videos.get(1).getUrl(), videoUpdated.getUrl());
        Assert.assertEquals(videos.get(1).getCategoriaId().getCategoria(), videoUpdated.getCategoriaId().getCategoria());
        Mockito.verify(repository).save(videoUpdated);
        Mockito.verify(repository).findById(2L);
        Mockito.verify(categoriaRepository, Mockito.never()).findById(newObj.getCategoria());
    }

    @Test
    public void testaUpdateQuandoAlgunsItensSaoAtualizadosEACategoriaTambem(){
        VideoUpdate newObj = new VideoUpdate();
        newObj.setDescricao("Nova Descricao-Atualizado");
        newObj.setCategoria(2L);

        List<Videos> videos = retornaVideos();
        List<Categoria> categorias = retornaCategorias();

        Mockito.when(repository.findById(2L)).thenReturn(Optional.ofNullable(videos.get(1)));
        Mockito.when(categoriaRepository.findById(newObj.getCategoria())).thenReturn(Optional.ofNullable(categorias.get(1)));

        service.update(2L, newObj);
        Mockito.verify(repository).save(captor.capture());
        Videos videoUpdated = captor.getValue();

        Assert.assertEquals("Nova Descricao-Atualizado", videoUpdated.getDescricao());
        Assert.assertEquals(videos.get(1).getTitulo(), videoUpdated.getTitulo());
        Assert.assertEquals(videos.get(1).getUrl(), videoUpdated.getUrl());
        Assert.assertEquals("Programação", videoUpdated.getCategoriaId().getCategoria());
        Mockito.verify(repository).save(videoUpdated);
        Mockito.verify(repository).findById(2L);
        Mockito.verify(categoriaRepository).findById(newObj.getCategoria());
    }

    @Test
    public void testaUpdateQuandoAlgunsItensSaoAtualizadosEACategoriaAtualizaComInexistente(){
        VideoUpdate newObj = new VideoUpdate();
        newObj.setDescricao("Nova Descricao-Atualizado");
        newObj.setCategoria(300L);

        List<Videos> videos = retornaVideos();
        List<Categoria> categorias = retornaCategorias();

        Mockito.when(repository.findById(2L)).thenReturn(Optional.ofNullable(videos.get(1)));
        Mockito.when(categoriaRepository.findById(newObj.getCategoria())).thenReturn(Optional.empty());

        service.update(2L, newObj);
        Mockito.verify(repository).save(captor.capture());
        Videos videoUpdated = captor.getValue();

        Assert.assertEquals("Nova Descricao-Atualizado", videoUpdated.getDescricao());
        Assert.assertEquals(videos.get(1).getTitulo(), videoUpdated.getTitulo());
        Assert.assertEquals(videos.get(1).getUrl(), videoUpdated.getUrl());
        Assert.assertEquals("Front-end", videoUpdated.getCategoriaId().getCategoria());
        Mockito.verify(repository).save(videoUpdated);
        Mockito.verify(repository).findById(2L);
        Mockito.verify(categoriaRepository).findById(newObj.getCategoria());
    }
}