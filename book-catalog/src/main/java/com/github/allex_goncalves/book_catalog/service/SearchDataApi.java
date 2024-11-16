package com.github.allex_goncalves.book_catalog.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchDataApi {
    private final ServiceAPI serviceAPI = new ServiceAPI();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String topBooksByLanguage(String jsonResponse, String language) {
        try {
            List<JsonNode> livrosCorrespondentes = new ArrayList<>();

            // Implementação DO While, para carregar todas as paginas, ficou muito pesado.
            // API já retorna por ordem de maior download, então só analisei a primeira pagina,
            // com 32 livros para listar os Top 10 de cada idioma.

                //do {
                    JsonNode rootNode = objectMapper.readTree(jsonResponse);
                    JsonNode resultsNode = rootNode.path("results");

                    if (!resultsNode.isArray() || resultsNode.isEmpty()) {
                        return "Nenhum livro encontrado para o idioma especificado.";
                    }

                    for(JsonNode bookNode : resultsNode) {
                        for (JsonNode languageNode : bookNode.path("languages")) {
                            if (languageNode.asText().equalsIgnoreCase(language)) {
                                livrosCorrespondentes.add(bookNode);
                                break;
                            }
                        }
                    }

                   // String nextUrl = rootNode.path("next").asText();
                    //if (!nextUrl.isEmpty() && !nextUrl.equals("null")) {
                        // Fazer nova requisição HTTP
                    //    jsonResponse = serviceAPI.getDataFromUrl(nextUrl);
                   // } else {
                    //    jsonResponse = null;
                   // }

               // } while (jsonResponse != null); //while (!jsonResponse.isEmpty() && !jsonResponse.equals("null"));

            if (livrosCorrespondentes.isEmpty()) {
                return "Livro não encontrado para este idioma especificado.";
            }

            // Coleta todos os livros encontrados, poderia ter feito direto retornando os 10 primeiros,
            // mas preferi está logica, pois serve para APIs sem ordenação, ou ordenar por outro tópico.

            livrosCorrespondentes.sort((book1, book2) -> Integer.compare(
                    book2.path("download_count").asInt(),
                    book1.path("download_count").asInt()));

            // Pega os 10 primeiros livros e imprime no console
            livrosCorrespondentes.stream()
                    .limit(10)
                    .forEach(bookNode -> {
                        String title = bookNode.path("title").asText();
                        int downloads = bookNode.path("download_count").asInt();
                        System.out.printf("Título: %s | Downloads: %d%n", title, downloads);
                    });

            return "Listagem concluída.";

        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao processar os dados da API: " + e.getMessage();
        }
    }

    public void searchDadosApi(String idioma) throws InterruptedException {
       String json = serviceAPI.buscarLivroIdioma(idioma);
       var result = topBooksByLanguage(json, idioma);
        System.out.printf(result);
    }

}
