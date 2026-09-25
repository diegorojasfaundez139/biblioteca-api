package com.diego.biblioteca.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta estándar para errores de la API")
public class ErrorResponse {

    @Schema(
            description = "Mensaje descriptivo del error",
            example = "Book not found with id: 999"
    )
    private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}