package com.lessalc.aluraflix.dto;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class VideoFormTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void verificaCampoVazioParaTitulo(){
        Set<ConstraintViolation<VideoForm>> violationSet = validator.validate(new VideoForm("", "Descricao do Video", "url.aas.teste", 1L));
        assertFalse(violationSet.isEmpty());
    }

    @Test
    public void verificaCampoNuloParaTitulo(){
        Set<ConstraintViolation<VideoForm>> violationSet = validator.validate(new VideoForm(null, "Descricao do Video", "url.aas.teste", 1L));
        assertFalse(violationSet.isEmpty());
    }

    @Test
    public void verificaCampoVazioParaDescricao(){
        Set<ConstraintViolation<VideoForm>> violationSet = validator.validate(new VideoForm("Titulo Video", "", "url.aas.teste", 1L));
        assertFalse(violationSet.isEmpty());
    }

    @Test
    public void verificaCampoInferior10CaracteresParaDescricao(){
        Set<ConstraintViolation<VideoForm>> violationSet = validator.validate(new VideoForm("Titulo Video", "123456789", "url.aas.teste", 1L));
        assertFalse(violationSet.isEmpty());
    }

    @Test
    public void verificaCampoNuloParaDescricao(){
        Set<ConstraintViolation<VideoForm>> violationSet = validator.validate(new VideoForm("Titulo Video", null, "url.aas.teste", 1L));
        assertFalse(violationSet.isEmpty());
    }

    @Test
    public void verificaCampoVazioParaUrl(){
        Set<ConstraintViolation<VideoForm>> violationSet = validator.validate(new VideoForm("Titulo Video", "Descricao do Video", "", 1L));
        assertFalse(violationSet.isEmpty());
    }

    @Test
    public void verificaCampoInferior10CaracteresParaUrl(){
        Set<ConstraintViolation<VideoForm>> violationSet = validator.validate(new VideoForm("Titulo Video", "Descricao do Video", "123456789", 1L));
        assertFalse(violationSet.isEmpty());
    }

    @Test
    public void verificaCampoNuloParaUrl(){
        Set<ConstraintViolation<VideoForm>> violationSet = validator.validate(new VideoForm("Titulo Video", "Descricao do Video", null, 1L));
        assertFalse(violationSet.isEmpty());
    }

    @Test
    public void verificaQueCategoriaPodeSerNula(){
        Set<ConstraintViolation<VideoForm>> violationSet = validator.validate(new VideoForm("Título", "Descricao do Video", "url.aas.teste"));
        assertTrue(violationSet.isEmpty());
    }

    @Test
    public void verificaQueCategoriaPodeSerInvalida(){
        Set<ConstraintViolation<VideoForm>> violationSet = validator.validate(new VideoForm("Título", "Descricao do Video", "url.aas.teste", 1000L));
        assertTrue(violationSet.isEmpty());
    }

    @Test
    public void deveriaCriarVideoFormCorretamenteSeRespeitarValidacoes(){
        Set<ConstraintViolation<VideoForm>> violationSet = validator.validate(new VideoForm("Título", "Descricao do Video", "url.aas.teste", 1L));
        assertTrue(violationSet.isEmpty());
    }
}