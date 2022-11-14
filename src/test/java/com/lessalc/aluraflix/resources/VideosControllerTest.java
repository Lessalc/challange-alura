package com.lessalc.aluraflix.resources;

import org.junit.Test;
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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VideosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveriaCadastrarUmNovoVideo() throws Exception {
        URI uri = new URI("/videos");
        String novoVideo = "{\"categoria\": 3,\"titulo\": \"Adicionado\",\"descricao\":\"Descricao de filme adicionado\",\"url\":\"http://url.aas.test2\"}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(novoVideo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(201));
    }

    @Test
    public void deveriaFalharEmCadastrarUmNovoVideoSemTitulo() throws Exception {
        URI uri = new URI("/videos");
        String novoVideo = "{\"categoria\": 3,\"descricao\":\"Descricao de filme adicionado\",\"url\":\"http://url.aas.test2\"}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(novoVideo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(400));
    }

    @Test
    public void deveriaCadastrarUmNovoVideoMesmoComCategoriaInexistenteEVerificaSeEhAtribuidaCategoria1L() throws Exception {
        URI uri = new URI("/videos");
        String novoVideo = "{\"categoria\": 20,\"titulo\": \"Adicionado\",\"descricao\":\"Descricao de filme adicionado\",\"url\":\"http://url.aas.test2\"}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(novoVideo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriaId.id").value(1L));
    }

    @Test
    public void deveriaCadastrarUmNovoVideoMesmoSemInserirCategoriaEVerificaSeEhAtribuidaCategoria1L() throws Exception {
        URI uri = new URI("/videos");
        String novoVideo = "{\"titulo\": \"Adicionado\",\"descricao\":\"Descricao de filme adicionado\",\"url\":\"http://url.aas.test2\"}";

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(novoVideo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriaId.id").value(1L));
    }

    @Test
    public void testaRetornoComId_1() throws Exception{
        URI uri = new URI("/videos");

        mockMvc.perform(MockMvcRequestBuilders
                        .get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoriaId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("Filme Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value("Essa é uma descricao de um filme teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url").value("http://url.aas.test"));
    }


    @Test
    public void verificaCamposNulosVaziosEAlteradosEmUmaAtualizacao() throws Exception{
        URI uri = new URI("/videos/1");
        String videoAlterado = "{\"titulo\": \"Novo Titulo, campo único\",\"url\":\"\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .put(uri)
                    .content(videoAlterado)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("Novo Titulo, campo único"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Essa é uma descricao de um filme teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("http://url.aas.test"));
    }

    @Test
    public void idNaoEncontradoParaGet() throws Exception{
        URI uri = new URI("/videos/500");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Não encontrado!"));
    }

    @Test
    public void idNaoEncontradoParaPut() throws Exception{
        URI uri = new URI("/videos/500");
        String videoAlterado = "{\"titulo\": \"Novo Titulo, campo único\",\"url\":\"\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .put(uri)
                    .content(videoAlterado)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Não encontrado!"));
    }

    @Test
    public void deveriaDeletarVideoExistente() throws Exception{
        URI uri = new URI("/videos/2");

        mockMvc.perform(MockMvcRequestBuilders
                    .delete(uri))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void deveriaFalharEmDeletarVideoInexistente() throws Exception{
        URI uri = new URI("/videos/100");

        mockMvc.perform(MockMvcRequestBuilders
                    .delete(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Não encontrado!"));
    }

}