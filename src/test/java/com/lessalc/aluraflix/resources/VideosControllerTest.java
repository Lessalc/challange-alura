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
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
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
    public void testaIntegracaoVideo_InsereNovoVideo_Ler_Altera_Deleta() throws Exception{
        // ******************TESTANDO CRIACAO DE VIDEO - INSERINDO CATEGORIA INEXISTENTE*******************************

        URI uri = new URI("/categorias");
        String categoriaNova = "{\"categoria\": \"Livre\",\n" +
                "\"cor\": \"Cinza\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(categoriaNova)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoria").value("Livre"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cor").value("Cinza"));

        URI uriVideo = new URI("/videos");
        String novoVideo = "{\"titulo\": \"Adicionado\",\"descricao\":\"Descricao de filme adicionado\",\"url\":\"http://url.aas.test\",\"categoria\":\"2\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .post(uriVideo)
                    .content(novoVideo)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriaId.id").value(1L));

        // ******************TESTANDO FALHA EM CRIAÇÃO DE VIDEO COM DADOS INCOMPLETOS*******************************
        uri = new URI("/videos");
        novoVideo = "{\"categoria\": 3,\"descricao\":\"Descricao de filme adicionado\",\"url\":\"http://url.aas.test2\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(novoVideo)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));

        // ******************TESTANDO BUSCA DE VIDEO POR ID*******************************
        uri = new URI("/videos/1");

        mockMvc.perform(MockMvcRequestBuilders
                    .get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriaId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("Adicionado"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Descricao de filme adicionado"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("http://url.aas.test"));

        // ******************TESTANDO ATUALIZACAO DE VIDEO*******************************
        uri = new URI("/videos/1");
        novoVideo = "{\"descricao\":\"Descricao de filme modificada\",\"url\":\"\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .put(uri)
                    .content(novoVideo)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriaId.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("Adicionado"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Descricao de filme modificada"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("http://url.aas.test"));

        // ******************TESTANDO BUSCA DE VIDEO POR TITULO*******************************
        uri = new URI("/videos/");

        mockMvc.perform(MockMvcRequestBuilders
                    .get(uri)
                    .queryParam("titulo","adicionado"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoriaId.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("Adicionado"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value("Descricao de filme modificada"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url").value("http://url.aas.test"));

        // ******************TESTANDO BUSCA DE VIDEO POR TITULO - NÃO EXISTENTE*******************************
        mockMvc.perform(MockMvcRequestBuilders
                    .get(uri)
                    .queryParam("titulo","Outro"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());

        // ******************DELETANDO VIDEO*******************************
        uri = new URI("/videos/1");

        mockMvc.perform(MockMvcRequestBuilders
                    .delete(uri))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}