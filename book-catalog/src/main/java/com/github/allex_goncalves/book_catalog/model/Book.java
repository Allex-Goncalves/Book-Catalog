package com.github.allex_goncalves.book_catalog.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table (name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Integer numeroDownloads;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "genero")
    private List<String> generos;


    @ManyToMany
    @JoinTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors = new ArrayList<>();

    public Book(){}
    public Book(DadosBook dadosBook){
        this.titulo = dadosBook.titulo();
        this.idioma = (dadosBook.idioma() != null && !dadosBook.idioma().isEmpty())
                ? dadosBook.idioma().getFirst()
                : null;

        this.numeroDownloads = dadosBook.numeroDownload();
        this.generos = Genre.extractGenres(dadosBook.generos().stream()
                .map(Genre::toString)
                .toList());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public Integer getNumeroDownloads() { return numeroDownloads; }
    public void setNumeroDowloads(Integer numeroDowloads) { this.numeroDownloads = numeroDowloads; }

    public List<Author> getAuthor() { return authors; }
    public void setAuthors(List<Author> authors) {
        authors.forEach(e -> e.getBooks().add(this));
        this.authors = authors; }

    public List<String> getGeneros() { return generos; }
    public void setGeneros(List<String> generos) { this.generos = generos; }

    @Override
    public String toString() {
        return
                ", Titulo='" + titulo + '\'' +
                        ", Autor=" + authors +
                        ", Idioma=" + idioma +
                        ", Gêneros='" + generos + '\'' +
                        ", N° Dowloads='" + numeroDownloads + '\'';

    }

}

