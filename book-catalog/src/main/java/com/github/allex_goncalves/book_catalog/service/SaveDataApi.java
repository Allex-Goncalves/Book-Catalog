package com.github.allex_goncalves.book_catalog.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.allex_goncalves.book_catalog.model.*;
import com.github.allex_goncalves.book_catalog.repository.AuthorRepository;
import com.github.allex_goncalves.book_catalog.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
@Service
public class SaveDataApi {
    private final ServiceAPI serviceAPI = new ServiceAPI();

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String salvarDadosLivro(String jsonResponse, String tituloDesejado) {
        try {
            List<JsonNode> livrosCorrespondentes = new ArrayList<>();

            do {
                JsonNode rootNode = objectMapper.readTree(jsonResponse);
                JsonNode resultsNode = rootNode.path("results");

                if (!resultsNode.isArray() || resultsNode.isEmpty()) {
                    return "Nenhum livro encontrado para o título especificado.";
                }
                System.out.println("numero de livros encontrados" + resultsNode.size());

                for (JsonNode bookNode : resultsNode) {
                    if (bookNode.path("title").asText().equalsIgnoreCase(tituloDesejado)) {
                        livrosCorrespondentes.add(bookNode);
                    }
                }

                String nextUrl = rootNode.path("next").asText();
                if (!nextUrl.isEmpty() && !nextUrl.equals("null")) {
                    jsonResponse = serviceAPI.getDataFromUrl(nextUrl);
                } else {
                    jsonResponse = null;

                }

                } while (jsonResponse != null);

            if (livrosCorrespondentes.isEmpty()) {
                return "Livro não encontrado para o título especificado.";
            }

            JsonNode livroParaSalvar = livrosCorrespondentes.size() > 1
                    ? livrosCorrespondentes.stream()
                    .max(Comparator.comparingInt(book -> book.path("download_count").asInt()))
                    .orElseThrow()
                    : livrosCorrespondentes.getFirst();

            DadosBook dadosLivro = objectMapper.convertValue(livroParaSalvar, DadosBook.class);

            Optional<Book> livroExistente = bookRepository.findByTitulo(dadosLivro.titulo());
            if (livroExistente.isPresent()) {
                return System.out.printf("Livro já cadastrado: " + dadosLivro.titulo()).toString();
            }

            Book book = new Book();
            book.setTitulo(dadosLivro.titulo());
            book.setIdioma(dadosLivro.idioma().getFirst());
            book.setNumeroDowloads(dadosLivro.numeroDownload());
            book.setGeneros(Genre.extractGenres(dadosLivro.generos()));

            JsonNode authorsArray = livroParaSalvar.path("authors");
            List<Author> authors = new ArrayList<>();
            if (authorsArray.isArray() && !authorsArray.isEmpty()) {
                for (JsonNode authorNode : authorsArray) {
                    DadosAuthor dadosAuthor = objectMapper.convertValue(authorNode, DadosAuthor.class);
                    String nomeAutor = dadosAuthor.nome().replace(",", "").trim();
                    
                    Optional<Author> authorOptional = authorRepository.findByNome(nomeAutor);
                    Author author = authorOptional.orElseGet(() -> {

                        Author newAuthor = new Author();
                        newAuthor.setNome(nomeAutor);
                        newAuthor.setAnoNascimento(dadosAuthor.anoNascimento());
                        newAuthor.setAnoFalecimento(dadosAuthor.anoFalecimento());
                        return authorRepository.save(newAuthor);
                    });

                    authors.add(author);
                }

            } else {
                Optional<Author> authorOptional = authorRepository.findByNome("Desconhecido");
                Author author = authorOptional.orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setNome("Desconhecido");
                    newAuthor.setAnoNascimento(1900);
                    newAuthor.setAnoFalecimento(1900);
                    return authorRepository.save(newAuthor);
                });

                authors.add(author);
            }
            book.setAuthors(authors);
            bookRepository.save(book);
            return System.out.printf("Livro salvo com sucesso: " + book.getTitulo()).toString();

        } catch (IOException e) {
            e.printStackTrace();
            return System.out.printf("Erro ao salvar dados do livro: " + e.getMessage()).toString();
        }
    }

   public String dadosAPI(String titulo) throws InterruptedException {
       String json = serviceAPI.buscarLivroPorTitulo(titulo);
       return salvarDadosLivro(json, titulo);
   }


}


