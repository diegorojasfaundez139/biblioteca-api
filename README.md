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