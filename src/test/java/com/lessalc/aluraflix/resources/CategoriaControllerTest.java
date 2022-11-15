package com.lessalc.aluraflix.resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
}