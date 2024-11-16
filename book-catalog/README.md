# Book Catalog Application: Uma Solução em Java para Gerenciamento de Livros

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

A **Book Catalog Application** é uma aplicação baseada em terminal desenvolvida em Java para gerenciar um catálogo de livros. Ela integra dados da [Gutendex API](https://gutendex.com/) e utiliza um banco de dados PostgreSQL local para armazenar e consultar informações, oferecendo uma navegação fácil por menu e funcionalidades avançadas de consulta e filtragem.

---

## **Recursos**

- **Busca e Salvamento**:
    - Pesquise livros na API Gutendex pelo título e salve no banco de dados PostgreSQL.
- **Consultas Avançadas**:
    - Procure livros salvos pelo título.
    - Filtre livros por idioma.
    - Liste e busque autores no banco de dados.
    - Filtre autores por critérios específicos, como ano de Falecimento.
- **Estatísticas**:
    - Exiba os 10 livros mais baixados.
    - Veja estatísticas de downloads por idioma.
- **Menu Intuitivo**:
    - Navegue facilmente por um menu baseado em terminal para gerenciar seu catálogo.

---
## **Tecnologias Utilizadas**

- **Java 21**: Linguagem principal da aplicação.
- **Spring Boot**: Framework para simplificar a injeção de dependências e configuração do aplicativo.
- **PostgreSQL**: Banco de dados para persistência de dados.
- **Jackson Databind**: Para manipulação de dados JSON da API.
- **Maven**: Gerenciamento de dependências e builds.

---

## **Pré-requisitos**

Antes de começar, certifique-se de ter instalado:

1. **Java 21**
2. **PostgreSQL** (versão 17 ou superior recomendada)
3. **Maven** (versão 4.0.0)
4. **Git** (opcional, para clonar o repositório)

As dependências são incluídas em `pom.xml`, como segue:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.17.2</version>
    </dependency>
</dependencies>
```
---



## **Instalação e Configuração**

1. **Passo 1: Clonar Repositório**:

    ```bash
    git clone https://github.com/Allex-Goncalves/Book-Catalog.git
    cd Book-Catalog
    ```

2. **Passo 2: Instalar dependências com Maven**:
   Certifique-se de ter o Maven instalado. Caso contrário, siga o [Guia de instalação do Maven](https://maven.apache.org/install.html). Em seguida, execute o seguinte comando para instalar as dependências necessárias:    
   ```bash
    mvn clean install
    ```

3. **Passo 3: Configure o Banco de Dados PostgreSQL**:
   - Configure o banco de dados PostgreSQL.
   - Edite o arquivo application.properties localizado em src/main/resources/ com suas credenciais do banco de dados.
   Exemplo de configuração em `application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/book_catalog
    spring.datasource.username=Seu_Usuario
    spring.datasource.password=Sua_Senha
    spring.jpa.hibernate.ddl-auto=update
    ```

4. **Passo 4: Executar a Aplicação**:
   Depois de configurar o banco de dados, inicie a aplicação com:

    ```bash
    mvn spring-boot:run
    ```

## Usando a Aplicação

Quando a aplicação estiver em execução, um menu será exibido no terminal com as seguintes opções:

1. **Buscar um livro pelo título na API e salvar no banco de dados**
2. **Buscar um livro pelo título no banco de dados**
3. **Listar todos os livros salvos no banco de dados**
4. **Listar livros por idioma no banco de dados**
5. **Buscar um autor pelo nome no banco de dados**
6. **Listar todos os autores no banco de dados**
7. **Listar autores vivos em um ano específico no banco de dados**
8. **Listar os 10 livros mais baixados do banco de dados**
9. **Exibir os 10 livros mais baixados da API em um idioma selecionado**
10. **Sair da aplicação**

### Exemplo de Uso

- **Buscar e Salvar**: Digite `1` para buscar um livro pelo título na API Gutendex e salvar os detalhes no seu banco de dados.
- **Listar Livros**: Escolha `3` para visualizar todos os livros catalogados no banco de dados.
- **Top Downloads**: Selecione `9` para exibir uma lista dos 10 livros mais baixados da API no idioma selecionado.

# Solução de Problemas

## Erros de Conexão com o Banco de Dados

1. **Certifique-se de que o PostgreSQL está em execução**: Verifique se o serviço do banco de dados PostgreSQL está rodando em sua máquina local ou em um servidor remoto.
2. **Verifique se as credenciais no arquivo `application.properties` estão corretas**: Confirme que o nome de usuário, senha e URL do banco de dados configurados no arquivo `application.properties` estão corretos e correspondem às configurações do banco de dados onde a aplicação está tentando se conectar.

## Limites da API
**Se encontrar problemas ao acessar a API Gutendex**: Verifique o status atual da API Gutendex, pois pode haver interrupções ou manutenção em andamento.

## Licença
Este projeto está licenciado sob a Licença MIT. Consulte o arquivo LICENSE para mais detalhes.