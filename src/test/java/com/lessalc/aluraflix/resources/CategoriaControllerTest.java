package com.lessalc.aluraflix.resources;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoriaControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testaFindAll()throws Exception {
        URI uri = new URI("/categorias");
        mockMvc.perform(MockMvcRequestBuilders
                    .get(uri))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].categoria").value("Programação"));
    }

    @Test
    public void deveriaRetornarCategoriaEspecificaPeloId() throws Exception{
        URI uri = new URI("/categorias/1");

        mockMvc.perform(MockMvcRequestBuilders
                    .get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoria").value("Livre"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cor").value("Cinza"));
    }

    @Test
    public void deveriaFalharEmRetornarCategoriaEspecificaQuandoIdNaoExiste() throws Exception{
        URI uri = new URI("/categorias/100");

        mockMvc.perform(MockMvcRequestBuilders
                    .get(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deveriaRetornarListaDeVideosPorCategoriaExistenteEComVideosAssociados() throws Exception{
        URI uri = new URI("/categorias/1/videos");

        mockMvc.perform(MockMvcRequestBuilders
                    .get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoriaId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("Filme Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value("Essa é uma descricao de um filme teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url").value("http://url.aas.test"));;
    }

    @Test
    public void deveriaRetornarListaVaziaDeVideosPorCategoriaExistenteSemVideosAssociados() throws Exception{
        URI uri = new URI("/categorias/2/videos");

        mockMvc.perform(MockMvcRequestBuilders
                    .get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void deveriaFalharEmRetornarVideosQuandoCategoriaNaoExiste() throws Exception{
        URI uri = new URI("/categorias/100/videos");

        mockMvc.perform(MockMvcRequestBuilders
                    .get(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deveriaCriarUmaNovaCategoriaQuandoDadosSaoPassadosCorretamente() throws Exception{
        URI uri = new URI("/categorias");
        String categoriaNova = "{\"categoria\": \"DevOps\",\n" +
                "\"cor\": \"Vermelho\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(categoriaNova)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoria").value("DevOps"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cor").value("Vermelho"));
    }

    @Test
    public void deveriaFalharEmCriarUmaNovaCategoriaQuandoCampoCategoriaEhVazio() throws Exception{
        URI uri = new URI("/categorias");
        String categoriaNova = "{\"categoria\": \"\",\n" +
                "\"cor\": \"Vermelho\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(categoriaNova)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("categoria"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].erro").value("must not be empty"));
    }

    @Test
    public void deveriaFalharEmCriarUmaNovaCategoriaQuandoCampoCategoriaEhNulo() throws Exception{
        URI uri = new URI("/categorias");
        String categoriaNova = "{\"cor\": \"Vermelho\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(categoriaNova)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("categoria"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].erro").value("must not be empty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].erro").value("must not be null"));
    }

    @Test
    public void deveriaFalharEmCriarUmaNovaCategoriaQuandoCampoCorEhVazio() throws Exception{
        URI uri = new URI("/categorias");
        String categoriaNova = "{\"categoria\": \"DevOps\",\n" +
                "\"cor\": \"\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(categoriaNova)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("cor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].erro").value("must not be empty"));
    }

    @Test
    public void deveriaFalharEmCriarUmaNovaCategoriaQuandoCampoCorEhNulo() throws Exception{
        URI uri = new URI("/categorias");
        String categoriaNova = "{\"categoria\": \"DevOps\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(categoriaNova)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("cor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].erro").value("must not be null"));
    }

    @Test
    public void deveriaAtualizarUmaCategoriaExistente() throws Exception{
        URI uri = new URI("/categorias/4");
        String categoriaNova = "{\"categoria\": \"DevOps\",\n" +
                "\"cor\": \"Vermelho\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .put(uri)
                    .content(categoriaNova)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoria").value("DevOps"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cor").value("Vermelho"));
    }

    @Test
    public void deveriaAtualizarSomenteOCampoCategoria() throws Exception{
        URI uri = new URI("/categorias/4");
        String categoriaNova = "{\"categoria\": \"DevOps\",\n" +
                "\"cor\": \"\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .put(uri)
                    .content(categoriaNova)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoria").value("DevOps"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cor").value("Verde-claro"));
    }

    @Test
    public void deveriaAtualizarSomenteOCampoCor() throws Exception{
        URI uri = new URI("/categorias/4");
        String categoriaNova = "{\"cor\": \"Vermelho\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .put(uri)
                    .content(categoriaNova)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoria").value("Data Science"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cor").value("Vermelho"));
    }

    @Test
    public void deveriaFalharEmAtualizarCategoriaInexistente() throws Exception{
        URI uri = new URI("/categorias/100");
        String categoriaNova = "{\"categoria\": \"DevOps\",\n" +
                "\"cor\": \"Vermelho\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .put(uri)
                    .content(categoriaNova)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deveriaSerCapazDeDeletarUmaCategoriaExistenteEVideosAssociadosDeveriamTerCategoria1() throws Exception{
        URI uri = new URI("/categorias/3");
        URI uriVideo = new URI("/videos/2");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uriVideo))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriaId").value(3L));
        mockMvc.perform(MockMvcRequestBuilders
                    .delete(uri))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        mockMvc.perform(MockMvcRequestBuilders
                    .get(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        mockMvc.perform(MockMvcRequestBuilders
                    .get(uriVideo))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriaId").value(1L));
    }

    @Test
    public void naoDeveriaSerCapazDeDeletarACategoria1() throws Exception{
        URI uri = new URI("/categorias/1");

        mockMvc.perform(MockMvcRequestBuilders
                    .delete(uri))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveriaFalharEmDeletarCategoriaInexistente() throws Exception{
        URI uri = new URI("/categorias/100");

        mockMvc.perform(MockMvcRequestBuilders
                    .delete(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}