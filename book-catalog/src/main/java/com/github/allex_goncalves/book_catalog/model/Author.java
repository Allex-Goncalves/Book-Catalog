package com.github.allex_goncalves.book_catalog.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();


    public Author() {}
    public Author(DadosAuthor dadosAuthor){
        this.nome = dadosAuthor.nome();
        this.anoNascimento = dadosAuthor.anoNascimento();
        this.anoFalecimento = dadosAuthor.anoFalecimento();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome;}
    public void setNome(String nome) { this.nome = nome; }
    public Integer getAnoNascimento() { return anoNascimento; }
    public void setAnoNascimento(Integer anoNascimento) { this.anoNascimento = anoNascimento; }
    public Integer getAnoFalecimento() { return anoFalecimento; }
    public void setAnoFalecimento(Integer anoFalecimento) { this.anoFalecimento = anoFalecimento; }
    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) {
        books.forEach(e -> e.getAuthor().add(this));
        this.books = books; }

    @Override
    public String toString() {
        return
                ", Nome='" + nome + '\'' +
                        ", Ano Nascimento=" + anoNascimento +
                        ", Ano Falecimento=" + anoFalecimento +
                        ", Livros='" + books + '\'';

    }
}
