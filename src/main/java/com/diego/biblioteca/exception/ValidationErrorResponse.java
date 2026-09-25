package com.diego.biblioteca.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Respuesta con errores de validación de los campos")
public class ValidationErrorResponse {

    @Schema(
            description = "Errores asociados a cada campo",
            example = "{\"title\":\"El título es obligatorio\",\"year\":\"El año debe ser mayor o igual a 1000\"}"
    )
    private Map<String, String> errors;

    public ValidationErrorResponse(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}