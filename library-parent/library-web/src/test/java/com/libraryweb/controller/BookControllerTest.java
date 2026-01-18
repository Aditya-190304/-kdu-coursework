package com.libraryweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryapi.CreateBookRequest;
import com.libraryservice.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
// i updated this import because the old one is deprecated in Spring Boot 3.4
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // i switched to @MockitoBean here, which is the new standard replacement for @MockBean
    @MockitoBean
    private BookService bookService;

    @Test
    @WithMockUser(roles = "MEMBER")
    void memberCannotCreateBook() throws Exception {
        // i did this to verify the security rule: MEMBERS cannot create books, only LIBRARIANS
        CreateBookRequest req = new CreateBookRequest();
        req.setTitle("Hacking Library");

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isForbidden()); // Expect 403
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void librarianCanCreateBook() throws Exception {
        // i did this to prove the positive case works
        CreateBookRequest req = new CreateBookRequest();
        req.setTitle("Valid Book");

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated()); // Expect 201
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void createBookValidationFails() throws Exception {
        // i did this to test the @NotBlank validation on the DTO
        CreateBookRequest req = new CreateBookRequest();
        req.setTitle(""); // Invalid title

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest()); // Expect 400
    }
}