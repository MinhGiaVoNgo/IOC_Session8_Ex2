package com.example.library.controller;

import com.example.library.dto.BookUpdateStockDTO;
import com.example.library.entity.Book;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PatchMapping("/update/{id}")
    public ResponseEntity<Book> updateBookStock(
            @PathVariable("id") Long id,
            @Valid @RequestBody BookUpdateStockDTO dto) {
        Book updatedBook = bookService.updateBook(id, dto);
        return ResponseEntity.ok(updatedBook);
    }
}
