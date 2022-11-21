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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoriaControllerTest {


    @Autowired
    private MockMvc mockMvc;

    private CategoriaController controller;

    @Test
    public void testaIntegracaoCategoria_InsereNovaCategoria_InsereNovoVideo_Ler_Altera_Deleta() throws Exception {

        // ******************TESTANDO CRIACAO DE CATEGORIAS 1 E 2*******************************

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

        categoriaNova = "{\"categoria\": \"Programação\",\n" +
                "\"cor\": \"Verde\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(categoriaNova)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoria").value("Programação"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cor").value("Verde"));


        // ******************TESTANDO CRIACAO DE VIDEO*******************************
        URI uriVideo = new URI("/videos");
        String novoVideo = "{\"titulo\": \"Adicionado\",\"descricao\":\"Descricao de filme adicionado\",\"url\":\"http://url.aas.test\",\"categoria\":\"2\"}";

        mockMvc
                .perform(MockMvcRequestBuilders
                    .post(uriVideo)
                    .content(novoVideo)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriaId.id").value(2L));


        // ******************TESTANDO LEITURA DE CATEGORIA*******************************
        uri = new URI("/categorias");

        mockMvc.perform(MockMvcRequestBuilders
                    .get(uri))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoria").value("Livre"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cor").value("Cinza"));

        // ******************TESTANDO LEITURA DE VIDEOS A PARTIR DE UMA CATEGORIA*******************************
        uri = new URI("/categorias/2/videos");

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].categoriaId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("Adicionado"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value("Descricao de filme adicionado"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url").value("http://url.aas.test"));

        // ******************TESTANDO ATUALIZAÇÃO DE CATEGORIA*******************************
        uri = new URI("/categorias/2");
        categoriaNova = "{\"cor\": \"Vermelho\"}";

        mockMvc.perform(MockMvcRequestBuilders
                    .put(uri)
                    .content(categoriaNova)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoria").value("Programação"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cor").value("Vermelho"));

        // ******************TESTANDO EXCLUSÃO DE CATEGORIA E VIDEOS DESSA CATEGORIA RECEBENDO CATEGORIA COMO LIVRE*******************************
        uri = new URI("/categorias/2");
        uriVideo = new URI("/videos/1");

        mockMvc.perform(MockMvcRequestBuilders
                    .get(uriVideo))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoriaId").value(2L));
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

        // ******************TESTANDO NÃO PERMISSÃO EM EXCLUSÃO DE CATEGORIA 1*******************************
        uri = new URI("/categorias/1");

        mockMvc.perform(MockMvcRequestBuilders
                    .delete(uri))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}