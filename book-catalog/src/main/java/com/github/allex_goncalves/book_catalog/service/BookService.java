package com.github.allex_goncalves.book_catalog.service;
import com.github.allex_goncalves.book_catalog.model.Author;
import com.github.allex_goncalves.book_catalog.DTO.BookDTO;
import com.github.allex_goncalves.book_catalog.model.Book;
import com.github.allex_goncalves.book_catalog.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookService {

    private Optional<Book> bookBusca;
    private List<Book> books = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    private BookRepository bookRepository;


    @Transactional
    public void buscarLivroTitulo(){
        System.out.print("Digite o Titulo do Livro: ");
        var titulo = scanner.nextLine().trim();
        bookBusca = bookRepository.findByTituloIgnoreCase(titulo);

        if (bookBusca.isPresent()) {
            Book b = bookBusca.get();
            List<String> autors = b.getAuthor().stream()
                    .map(Author::getNome) // Extrai apenas o título de cada livro
                    .toList();
            BookDTO bookDTO = new BookDTO(
                    b.getTitulo(),
                    b.getIdioma(),
                    autors,
                    b.getNumeroDownloads(),
                    b.getGeneros()
            );
            System.out.println(bookDTO);
        } else {
            System.out.println("Livro não encontrado!");
        }
    }

    @Transactional
    public void listarLivros(){
        books = bookRepository.findAll();
        if (books.isEmpty()) {
            System.out.println("Nenhum livro encontrado no banco de dados.");
        } else {
            books.stream()
                    .map(b -> new BookDTO(
                            b.getTitulo(),
                            b.getIdioma(),
                            b.getAuthor().stream().map(Author::getNome).toList(),
                            b.getNumeroDownloads(),
                            b.getGeneros()))
                    .forEach(bookDTO -> {
                        System.out.println(bookDTO);
                        System.out.println();
                    });
        }
    }


    @Transactional
    public void listarTop10LivrosMaisBaixados() {
        books = bookRepository.findAll();
        if (books.isEmpty()) {
            System.out.println("Nenhum livro encontrado no banco de dados.");
        } else {
            List<String> top10Livros = books.stream()
                    .sorted(Comparator.comparingInt(Book::getNumeroDownloads).reversed())
                    .limit(10)
                    .map(book -> String.format("Título: %s, Downloads: %d",
                            book.getTitulo(),
                            book.getNumeroDownloads()))
                    .toList();

            System.out.println("Top 10 Livros Mais com mais Downloads do banco de dados:");
            top10Livros.forEach(System.out::println);
        }
    }



    @Transactional
    public void listarLivrosIdioma(String idioma){
        books = bookRepository.findByIdioma(idioma);
        if (books.isEmpty()) {
            System.out.println("Nenhum livro encontrado no banco de dados para este idioma.");
        } else {
            books.stream()
                    .map(b -> new BookDTO(
                            b.getTitulo(),
                            b.getIdioma(),
                            b.getAuthor().stream().map(Author::getNome).toList(),
                            b.getNumeroDownloads(),
                            b.getGeneros()))
                    .forEach(bookDTO -> {
                        System.out.println(bookDTO);
                        System.out.println();
                    });
        }
    }
}
