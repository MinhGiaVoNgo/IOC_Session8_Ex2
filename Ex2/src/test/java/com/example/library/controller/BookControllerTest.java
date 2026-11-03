package com.example.library.controller;

import com.example.library.dto.BookUpdateStockDTO;
import com.example.library.entity.Book;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("PATCH /api/books/update/{id} - Valid stock should update and return 200 OK")
    void testUpdateBookStock_Success() throws Exception {
        BookUpdateStockDTO dto = BookUpdateStockDTO.builder().stock(25).build();
        Book updatedBook = Book.builder()
                .id(1L)
                .title("Clean Code")
                .author("Robert C. Martin")
                .stock(25)
                .build();

        when(bookService.updateBook(eq(1L), any(BookUpdateStockDTO.class))).thenReturn(updatedBook);

        mockMvc.perform(patch("/api/books/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.stock").value(25));
    }

    @Test
    @DisplayName("PATCH /api/books/update/{id} - Negative stock should return 400 Bad Request")
    void testUpdateBookStock_NegativeStock_BadRequest() throws Exception {
        BookUpdateStockDTO dto = BookUpdateStockDTO.builder().stock(-5).build();

        mockMvc.perform(patch("/api/books/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PATCH /api/books/update/{id} - Not found ID should return 404 Not Found")
    void testUpdateBookStock_NotFound() throws Exception {
        BookUpdateStockDTO dto = BookUpdateStockDTO.builder().stock(10).build();

        when(bookService.updateBook(eq(999L), any(BookUpdateStockDTO.class)))
                .thenThrow(new ResourceNotFoundException("Sách không tồn tại với ID: 999"));

        mockMvc.perform(patch("/api/books/update/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}
