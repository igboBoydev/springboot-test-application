package com.TestApplication.Services;


import com.TestApplication.Models.Books;
import com.TestApplication.Repository.BooksRepository;
import com.TestApplication.Repository.UsersRepository;
import com.TestApplication.Schemas.CreateBookRequest;
import com.TestApplication.Schemas.ResponseMessage;
import com.TestApplication.Schemas.UpdateBookRequest;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BooksService {
    private final BooksRepository booksRepository;
    private final UsersRepository usersRepository;

    public Page<Books> getAllBooks (int page, int size){
        PageRequest request = PageRequest.of(page, size);

        Page<Books> books = booksRepository.findAll(request);

        if(books.isEmpty()){
            return Page.empty();
        }
        return booksRepository.findAll(request);
    }

    public ResponseMessage createBook(CreateBookRequest request){
        String uuid = String.valueOf(UUID.randomUUID());
        Books books = new Books(request.getStatus(), request.getName(), request.getAuthor_name(), uuid);

        booksRepository.save(books);

        return ResponseMessage.builder().message("Book added successfully").statusCode(200).build();
    }

    public ResponseMessage updateBook(UpdateBookRequest request){
        Optional<Books> books = booksRepository.getBookByUuid(request.getUuid());

        if(books.isEmpty()){
            return ResponseMessage.builder().message("Book not found").statusCode(404).build();
        }

        books.get().setAuthor_name(request.getAuthor_name());
        books.get().setName(request.getName());
        books.get().setStatus(request.getStatus());

        booksRepository.save(books.get());

        return ResponseMessage.builder().message("Book updated successfully").statusCode(200).build();
    }

    public ResponseMessage deleteBookByUuid(String uuid){
        Optional<Books> books = booksRepository.getBookByUuid(uuid);

        if(StringUtils.isBlank(uuid) || StringUtils.isEmpty(uuid)){
            return ResponseMessage.builder().message("uuid cannot be empty").statusCode(400).build();
        }

        if(books.isEmpty()){
            return ResponseMessage.builder().message("book not found").statusCode(404).build();
        }

        booksRepository.deleteById(books.get().getId());

        return ResponseMessage.builder().message("book deleted successfully").statusCode(200).build();
    }
}
