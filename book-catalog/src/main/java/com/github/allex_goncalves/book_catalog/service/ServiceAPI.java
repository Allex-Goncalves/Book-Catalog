package com.github.allex_goncalves.book_catalog.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class ServiceAPI {

    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(Redirect.NORMAL)
            .build();

    // Este metodo verifica apenas a primeira pagina da consulda a API.
    public String obterDados(String endereco) throws InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new RuntimeException("Erro na requisição: Código de status " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao obter dados da API: " + e.getMessage(), e);
        }
    }

    public String buscarLivroPorTitulo(String titulo) throws InterruptedException {
        String apiUrl = "http://gutendex.com/books?search=" + URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        return obterDados(apiUrl);
    }

    public String buscarLivroIdioma(String idioma) throws InterruptedException {
        String apiUrl = "http://gutendex.com/books?languages=" + URLEncoder.encode(idioma, StandardCharsets.UTF_8);
        return obterDados(apiUrl);
    }

    // Este metodo foi criado para verificar todas as paginas da consulta feita a API, achei mais fácil
    // fazer dois metodos dentro do meu contexto.

    public String getDataFromUrl(String urlString) throws IOException {
        StringBuilder response = new StringBuilder();

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");


        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream inputStream = connection.getInputStream()) {

                byte[] data = inputStream.readAllBytes();
                response.append(new String(data, StandardCharsets.UTF_8));
            }
        } else {
            throw new IOException("Erro na requisição: Código de resposta " + responseCode);
        }

        connection.disconnect();
        return response.toString();
    }
}



