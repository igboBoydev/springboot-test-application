package com.TestApplication;

import com.TestApplication.Controllers.BooksController;
import com.TestApplication.Models.Books;
import com.TestApplication.Repository.BooksRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
class TestApplicationTests {
    private MockMvc mockMvc;

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BooksController booksController;

    String uuid = String.valueOf(UUID.randomUUID());
    Books book = new Books("available", "API test 101", uuid, "John Doe");
    Books book2 = new Books("sold", "API test 102", uuid, "Kennedy");


    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(booksController).build();
    }

    @Test
    public void getAllBooks() throws Exception{
        List<Books> books = new ArrayList<>(Arrays.asList(book, book2));

        Mockito.when(booksRepository.findAll()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books?page=0&size=6").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

}
