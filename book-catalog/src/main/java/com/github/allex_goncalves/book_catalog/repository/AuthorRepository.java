package com.github.allex_goncalves.book_catalog.repository;

import com.github.allex_goncalves.book_catalog.model.Author;
import com.github.allex_goncalves.book_catalog.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNome(String nome);
    List<Author> findAll();

    @Query("SELECT a FROM Author a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Optional<Author> findByNomeContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT a FROM Author a WHERE a.anoNascimento < :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Author> findAuthorsByYear(@Param("ano") Integer ano);
}
