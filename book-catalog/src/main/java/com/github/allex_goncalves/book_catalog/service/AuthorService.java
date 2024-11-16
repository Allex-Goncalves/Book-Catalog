package com.github.allex_goncalves.book_catalog.service;

import com.github.allex_goncalves.book_catalog.DTO.AuthorDTO;
import com.github.allex_goncalves.book_catalog.model.Author;
import com.github.allex_goncalves.book_catalog.model.Book;
import com.github.allex_goncalves.book_catalog.repository.AuthorRepository;
import com.github.allex_goncalves.book_catalog.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class AuthorService {

    private Optional<Author> authorBusca;
    private List<Author> authors;
    private final Scanner scanner = new Scanner(System.in);
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;



    @Transactional
    public void buscarAutorPorNome() {
        System.out.print("Digite o Nome do Autor: ");
        var nomeAutor = scanner.nextLine().trim();
        authorBusca = authorRepository.findByNomeContainingIgnoreCase(nomeAutor);

        if (authorBusca.isPresent()) {
            Author autor = authorBusca.get();
            List<String> titulos = autor.getBooks().stream()
                    .map(Book::getTitulo)
                    .toList();

            AuthorDTO authorDTO = new AuthorDTO(
                    autor.getNome(),
                    autor.getAnoNascimento(),
                    autor.getAnoFalecimento(),
                    titulos
            );
            System.out.println(authorDTO);
        } else {
            System.out.println("Autor não encontrado!");
        }
    }


    @Transactional
    public void listarAutores(){
        authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            System.out.println("Nenhum Autor encontrado no banco de dados.");
        } else {
            authors.stream()
                    .map(a -> new AuthorDTO(
                            a.getNome(),
                            a.getAnoNascimento(),
                            a.getAnoFalecimento(),
                            a.getBooks().stream().map(Book::getTitulo).toList()))
                    .forEach(authorDTO -> {
                        System.out.println(authorDTO);
                        System.out.println();
                    });
        }
    }

    @Transactional
    public void buscarAutorData() {
        System.out.print("Digite o Ano desejado: ");

        int ano = -1;
        boolean validInput = false;
        int currentYear = Year.now().getValue();

        while (!validInput) {
            try {
                ano = scanner.nextInt();

                if (String.valueOf(ano).length() == 4 && ano <= currentYear) {
                    validInput = true;
                } else {
                    System.out.println("Por favor, digite um ano válido (4 dígitos e menor ou igual ao ano vigente).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um ano válido (4 dígitos e menor ou igual ao ano vigente).");
                scanner.next();
            }
        }

        authors = authorRepository.findAuthorsByYear(ano);

        if (authors.isEmpty()){
            System.out.println("Nenhum Autor vivo para este ano");

        } else {
            authors.stream()
                    .map(a -> new AuthorDTO(
                            a.getNome(),
                            a.getAnoNascimento(),
                            a.getAnoFalecimento(),
                            a.getBooks().stream().map(Book::getTitulo).toList()))
                    .forEach(authorDTO -> {
                        System.out.println(authorDTO);
                        System.out.println();
                    });

        }

    }



}
