package com.github.allex_goncalves.book_catalog.DTO;

import java.util.List;

public record BookDTO (String titulo,
                       String idioma,
                       List<String> autors,
                       Integer numeroDownloads,
                       List<String> generos){


    @Override
    public String toString() {
        return "Título: " + titulo + "\n" +
                "Idioma: " + idioma + "\n" +
                "Autor: " + autors + "\n" +
                "Número de Downloads: " + numeroDownloads + "\n" +
                "Gênero: " + generos;
    }

}
