package com.diego.biblioteca.controller;

import com.diego.biblioteca.exception.ValidationErrorResponse;
import com.diego.biblioteca.model.Book;
import com.diego.biblioteca.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.diego.biblioteca.dto.BookRequest;
import jakarta.validation.Valid;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


import com.diego.biblioteca.exception.ErrorResponse;

@RestController
@RequestMapping("/books")
@Tag(
        name = "Books",
        description = "Operaciones relacionadas con libros"
)
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(
            summary = "Obtener todos los libros",
            description = "Devuelve la lista completa de libros"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Libros obtenidos correctamente"
    )
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @Operation(
            summary = "Obtener un libro por ID",
            description = "Devuelve un libro específico utilizando su identificador"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Libro encontrado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Libro no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @Operation(
            summary = "Crear un libro",
            description = "Crea un nuevo libro en la biblioteca"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Libro creado correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Los datos del libro no son válidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ValidationErrorResponse.class
                            )
                    )
            )
    })
    @PostMapping
    public Book createBook(@Valid @RequestBody BookRequest request) {

        Book book = new Book();

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setYear(request.getYear());
        book.setCategory(request.getCategory());

        return bookService.save(book);
    }

    @Operation(
            summary = "Eliminar un libro",
            description = "Elimina un libro existente utilizando su ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Libro eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Libro no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        bookService.findById(id);
        bookService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Actualizar un libro",
            description = "Actualiza los datos de un libro existente utilizando su ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Libro actualizado correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Los datos del libro no son válidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ValidationErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Libro no encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest request) {

        Book book = bookService.findById(id);

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setYear(request.getYear());
        book.setCategory(request.getCategory());

        Book updatedBook = bookService.save(book);

        return ResponseEntity.ok(updatedBook);
    }

    @Operation(
            summary = "Buscar libros",
            description = "Busca libros por título o autor"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Libros encontrados correctamente"
    )
    @GetMapping("/search")
    public List<Book> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {

        if (title != null) {
            return bookService.searchByTitle(title);
        }

        if (author != null) {
            return bookService.searchByAuthor(author);
        }

        return bookService.findAll();
    }
}