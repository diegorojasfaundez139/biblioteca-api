# Biblioteca API

La aplicación permite gestionar libros y usuarios de una biblioteca
mediante una API REST conectada a PostgreSQL.
## Tecnologías

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit
- Mockito
- Git
- GitHub

## Endpoints

### Libros

GET /books

GET /books/{id}

GET /books/search?title=java

GET /books/search?author=martin

POST /books

PUT /books/{id}

DELETE /books/{id}

## Usuarios

GET /users

GET /users/{id}

POST /users

PUT /users/{id}

DELETE /users/{id}

## Préstamos

POST /loans

GET /loans

GET /loans/{id}

PUT /loans/{id}/return

## Códigos HTTP

| Código | Significado |
|---|---|
| 200 | Operación exitosa |
| 201 | Recurso creado |
| 204 | Recurso eliminado |
| 400 | Petición inválida |
| 404 | Recurso no encontrado |
| 409 | Conflicto |
