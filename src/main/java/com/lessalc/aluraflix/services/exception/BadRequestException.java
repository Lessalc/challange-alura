package com.lessalc.aluraflix.services.exception;

public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestException() {
        super("Categoria 1 não pode ser excluída!");
    }

}
