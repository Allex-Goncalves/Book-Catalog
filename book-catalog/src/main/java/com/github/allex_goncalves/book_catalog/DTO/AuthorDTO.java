package com.github.allex_goncalves.book_catalog.DTO;

import java.util.List;

public record AuthorDTO(String nome,
                        Integer anoNascimento,
                        Integer anoFalecimento,
                        List<String> books){


    @Override
    public String toString() {
        return "Nome: " + nome + "\n" +
                "Ano de Nascimento: " + anoNascimento + "\n" +
                "Ano de Falecimento: " + anoFalecimento + "\n" +
                "Livros: " + books;
    }

}
