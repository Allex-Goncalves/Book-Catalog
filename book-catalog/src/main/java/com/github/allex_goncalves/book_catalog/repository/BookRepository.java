package com.github.allex_goncalves.book_catalog.repository;

import com.github.allex_goncalves.book_catalog.DTO.BookDTO;
import com.github.allex_goncalves.book_catalog.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAll();
    List<Book> findByIdioma(String idioma);
    Optional<Book> findByTitulo(String titulo);
    //List<Book> findByAuthor_Nome(String nome);
    @Query("SELECT b FROM Book b WHERE LOWER(b.titulo) = LOWER(:titulo)")
    Optional<Book> findByTituloIgnoreCase(@Param("titulo") String titulo);

}
