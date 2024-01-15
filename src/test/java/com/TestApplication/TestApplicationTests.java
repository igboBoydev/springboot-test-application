package com.TestApplication;

import com.TestApplication.Models.Books;
import com.TestApplication.Repository.BooksRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

//@RunWith(MockitoJUnitRunner.class)

@RunWith(SpringRunner.class)
@SpringBootTest
class TestApplicationTests {

    @Autowired
    private BooksRepository booksRepository;


    @Test
    void itShouldCheckIFBookExistsUuid(){
        String uuid = String.valueOf(UUID.randomUUID());
        //TODO: create a book model
        Books books = new Books("available", "API test 101", uuid, "John Doe");

        //TODO: save model to books database
        booksRepository.save(books);

        //TODO: get book by uuid
        Optional<Books> books1 = booksRepository.getBookByUuid(uuid);
        boolean isExist = books1.isPresent();

        //TODO: assert that book with uuid provided exists on the database
        AssertionsForClassTypes.assertThat(isExist).isTrue();
    }

}
