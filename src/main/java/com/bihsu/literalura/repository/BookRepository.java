package com.bihsu.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bihsu.literalura.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

    boolean existsByTitle(String title);
    Book findByTitle(String title);
}
