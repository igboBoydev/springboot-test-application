package com.TestApplication.Controllers;


import com.TestApplication.Models.Books;
import com.TestApplication.Schemas.*;
import com.TestApplication.Services.BooksService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
public class BooksController {
    private final BooksService booksService;

    @GetMapping
    public List<BooksResponse> getAllBooks(@RequestParam("page") int page, @RequestParam("size") int size){
        Page<Books> books = booksService.getAllBooks(page, size);
        List<BooksResponse> booksResponses = new ArrayList<>();
        for (Books i : books){
            booksResponses.add(BooksResponse.builder().uuid(i.getUuid()).name(i.getName()).author_name(i.getAuthor_name()).status(i.getStatus()).build());
        }
        return booksResponses;
    }


    @PostMapping
    public ResponseMessage createBook(@RequestBody @Valid CreateBookRequest request){
        return booksService.createBook(request);
    }

    @PutMapping("/update")
    public ResponseMessage updateBook(@RequestBody @Valid UpdateBookRequest request){
        return booksService.updateBook(request);
    }


    @DeleteMapping("/delete")
    public ResponseMessage deleteBookByUuid(@RequestParam("uuid") String uuid){
        return booksService.deleteBookByUuid(uuid);
    }

    @PutMapping("/status")
    public ResponseMessage updateBookStatus(@RequestParam("uuid") String uuid, @RequestParam("status") String status){
        System.out.println("mmmmmmmmmmmmmmmmmmmmmm");
        return booksService.updateBookStatus(uuid, status);
    }

}
