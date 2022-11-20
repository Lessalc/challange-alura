package com.lessalc.aluraflix.services;

import com.lessalc.aluraflix.dto.CategoriaUpdate;
import com.lessalc.aluraflix.dto.VideoForm;
import com.lessalc.aluraflix.entities.Categoria;
import com.lessalc.aluraflix.entities.Videos;
import com.lessalc.aluraflix.repositories.CategoriaRepository;
import com.lessalc.aluraflix.repositories.VideosRepository;
import com.lessalc.aluraflix.services.exception.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    private CategoryService service;
    @Mock
    private CategoriaRepository categoriaRepository;

    @Captor
    private ArgumentCaptor<Categoria> captor;

    private List<Categoria> categorias;
    private List<Videos> videos;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(categoriaRepository);
        service = new CategoryService(categoriaRepository);
    }

    @Before
    public void populaDados(){
        categorias = retornaCategorias();
        videos = retornaVideos();

        for (Categoria categoria : categorias) {
            categoria.setVideos(videos.stream().filter(v ->
                    v.getCategoriaId().getId().equals(categoria.getId())).collect(Collectors.toList()));
        }
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
    public void testaUpdateComAtualizacaoCompleta(){
        CategoriaUpdate update = new CategoriaUpdate("DevOps", "Vermelho");
        Mockito.when(categoriaRepository.findById(4L)).thenReturn(Optional.ofNullable(categorias.get(3)));

        service.update(4L, update);
        Mockito.verify(categoriaRepository).save(captor.capture());
        Categoria categoriaAtualizada = captor.getValue();

        Assert.assertEquals(update.getCategoria(), categoriaAtualizada.getCategoria());
        Assert.assertEquals(update.getCor(), categoriaAtualizada.getCor());
        Mockito.verify(categoriaRepository).findById(4L);
        Mockito.verify(categoriaRepository).save(categoriaAtualizada);
    }


    @Test
    public void testaUpdateComAtualizacaoParcial(){
        CategoriaUpdate update = new CategoriaUpdate();
        update.setCategoria("DevOps");
        update.setCategoria("DevOps");

        Mockito.when(categoriaRepository.findById(4L)).thenReturn(Optional.ofNullable(categorias.get(3)));

        service.update(4L, update);
        Mockito.verify(categoriaRepository).save(captor.capture());
        Categoria categoriaAtualizada = captor.getValue();

        Assert.assertEquals(update.getCategoria(), categoriaAtualizada.getCategoria());
        Assert.assertEquals("Verde-claro", categoriaAtualizada.getCor());
        Mockito.verify(categoriaRepository).findById(4L);
        Mockito.verify(categoriaRepository).save(categoriaAtualizada);
    }


    @Test
    public void testaUpdateComCamposVazios(){
        CategoriaUpdate update = new CategoriaUpdate("", "Vermelho");
        Mockito.when(categoriaRepository.findById(2L)).thenReturn(Optional.ofNullable(categorias.get(1)));

        service.update(2L, update);
        Mockito.verify(categoriaRepository).save(captor.capture());
        Categoria categoriaAtualizada = captor.getValue();

        Assert.assertEquals("Programação", categoriaAtualizada.getCategoria());
        Assert.assertEquals("Vermelho", categoriaAtualizada.getCor());
        Mockito.verify(categoriaRepository).findById(2L);
        Mockito.verify(categoriaRepository).save(categoriaAtualizada);
    }

    @Test
    public void testaDeletarUmaCategoriaComIdDiferenteDe1(){
        Mockito.when(categoriaRepository.findById(2L)).thenReturn(Optional.ofNullable(categorias.get(1)));
        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.ofNullable(categorias.get(0)));

        service.deletarCategoria(2L);
        Assert.assertEquals(categorias.get(0), videos.get(2).getCategoriaId());
        Assert.assertEquals(categorias.get(0), videos.get(3).getCategoriaId());
    }

    @Test
    public void retornaErroQuandoCategoriaNaoExiste(){
        Mockito.when(categoriaRepository.findById(10L)).thenReturn(Optional.empty());
        Assert.assertThrows(ResourceNotFoundException.class, () -> service.findCategoria(10L));
    }



}