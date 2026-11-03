package com.example.library.service;

import com.example.library.dto.BookUpdateStockDTO;
import com.example.library.entity.Book;

public interface BookService {
    Book updateBook(Long id, BookUpdateStockDTO dto);
    Book getBookById(Long id);
}
