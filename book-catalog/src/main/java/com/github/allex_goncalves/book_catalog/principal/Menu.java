package com.github.allex_goncalves.book_catalog.principal;
import com.github.allex_goncalves.book_catalog.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.*;

@Component
public class Menu {

    private SearchDataApi searchDataApi;
    private SaveDataApi saveDataApi;
    private final BookService bookService;
    private final AuthorService authorService;

    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public Menu(SaveDataApi saveDataApi, SearchDataApi searchDataApi, BookService bookService, AuthorService authorService) {
        this.saveDataApi = saveDataApi;
        this.searchDataApi = searchDataApi;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    public void showMenu() throws IOException, InterruptedException {

        while (true) {
            System.out.println("***************************************************************");
            System.out.println("--------------- Benvindo ao Buscador de Livros ----------------");
            System.out.println("***************************************************************\n");
            System.out.println("1 - Buscar livro pelo título na API e salvar no banco de dados");
            System.out.println("2 - Buscar livro pelo titulo no banco de dados");
            System.out.println("3 - Listar livros cadastrados no banco de dados");
            System.out.println("4 - Listar livros de um determinado idioma, no banco de dados");
            System.out.println("5 - Buscar pelo nome autores cadastrados no banco de dados");
            System.out.println("6 - Listar autores cadastrados no banco de dados");
            System.out.println("7 - Listar autores vivos em um determinado ano, no banco de dados ");
            System.out.println("8 - Listar os 10 livros com mais downloads do banco de Dados ");
            System.out.println("9 - Mostrar os top 10 livros mais baixados na API no idioma escolhido");
            System.out.println("10 - Sair\n");
            System.out.println("***************************************************************");
            System.out.println("---------------------------------------------------------------");
            System.out.println("***************************************************************\n");
            System.out.print("Escolha uma opção do Menu: ");
            int choice = -1;
            boolean validInput = false;

            while (!validInput) {

                try {
                    choice = scanner.nextInt();
                    validInput = true;

                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    scanner.next();
                }
            }
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Digite o título do livro: ");
                    String title = scanner.nextLine();
                    var result = saveDataApi.dadosAPI(title);
                    System.out.printf(result);
                    break;
                case 2:
                    bookService.buscarLivroTitulo();
                    break;
                case 3:
                    bookService.listarLivros();
                    break;
                case 4:
                    showIdiomasMenu();
                    break;
                case 5:
                    authorService.buscarAutorPorNome();
                    break;
                case 6:
                    authorService.listarAutores();
                    break;
                case 7:
                    authorService.buscarAutorData();
                    break;
                case 8:
                    bookService.listarTop10LivrosMaisBaixados();
                    break;
                case 9:
                    String idioma = "";
                    String valido = "";
                    do {
                        System.out.println("pt - Português");
                        System.out.println("en - Inglês");
                        System.out.println("es - Espanhol");
                        System.out.println("fr - Francês");
                        System.out.println("it - Italiano\n");
                        System.out.print("Digite o idioma do livro: ");

                        boolean validInput1 = false;
                        while (!validInput1) {
                            try {
                                idioma = scanner.nextLine();
                                validInput1 = true;

                                if(idioma.equals("pt") || idioma.equals("en") || idioma.equals("es")
                                        || idioma.equals("fr") || idioma.equals("it")){
                                    valido = "ok";
                                } else { System.out.println("Digite a sigla dos idiomas\n");}
                            } catch (InputMismatchException e) {
                                System.out.println("Entrada inválida. Por favor, digite a sigla do idioma\n");
                                scanner.next();
                            }
                        }

                    } while (!valido.equals("ok"));
                    searchDataApi.searchDadosApi(idioma);
                    break;
                case 10:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void showIdiomasMenu() {
        int choice = -1;
        do {
            System.out.println("Escolha uma das Opções\n");
            System.out.println("1 - Português ");
            System.out.println("2 - Inglês ");
            System.out.println("3 - Espanhol ");
            System.out.println("4 - Francês ");
            System.out.println("5 - Italiano ");
            System.out.print("Escolha uma opção de Idioma: ");

            boolean validInput = false;

            while (!validInput) {
                try {
                    choice = scanner.nextInt();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    scanner.next();
                }
            }

            switch (choice) {
                case 1:
                    bookService.listarLivrosIdioma("pt");
                    break;
                case 2:
                    bookService.listarLivrosIdioma("en");
                    break;

                case 3:
                    bookService.listarLivrosIdioma("es");
                    break;

                case 4:
                    bookService.listarLivrosIdioma("fr");
                    break;

                case 5:
                    bookService.listarLivrosIdioma("it");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (choice == 0 || choice > 5 );
    }

}
