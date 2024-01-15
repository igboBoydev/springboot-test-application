package com.TestApplication.Repository;


import com.TestApplication.Models.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {

    @Query(value = "select e.id, e.status, e.name, e.uuid, e.author_name, e.created_at, e.updated_at from books e where uuid = :uuid", nativeQuery = true)
    Optional<Books> getBookByUuid(String uuid);
}
