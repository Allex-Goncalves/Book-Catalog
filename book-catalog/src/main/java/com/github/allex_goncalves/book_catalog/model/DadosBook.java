package com.github.allex_goncalves.book_catalog.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosBook(@JsonAlias("title") String titulo,
                        @JsonAlias("languages") List<String> idioma,
                        @JsonAlias("download_count") Integer numeroDownload,
                        @JsonAlias("bookshelves") List<String> generos){
}