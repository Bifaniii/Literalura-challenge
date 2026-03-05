# 📚 Literalura - Challenge Alura

O **Literalura** é uma aplicação de console desenvolvida em Java com Spring Boot, criada como desafio para o programa ONE (Oracle Next Education). O objetivo é consumir a API **Gutendex**, realizar buscas de livros e autores, e persistir esses dados em um banco de dados relacional **PostgreSQL**.

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3+**
* **Spring Data JPA**
* **PostgreSQL** (Banco de dados)
* **Jackson** (Manipulação de JSON)
* **Maven** (Gerenciador de dependências)

## 🚀 Funcionalidades

O programa oferece um menu interativo no console com as seguintes opções:

1.  **Buscar livro pelo título:** Consulta a API Gutendex e salva no banco de dados.
2.  **Listar livros registrados:** Exibe todos os livros armazenados no banco local.
3.  **Listar autores registrados:** Exibe os autores salvos no banco.
4.  **Listar autores vivos em um determinado ano:** Filtra autores com base em datas históricas.
5.  **Listar livros por idioma:** Filtra os livros pelo código do idioma (ex: `pt`, `en`, `es`).

## 📋 Como Executar o Projeto

### Pré-requisitos
* Java JDK 17 instalado.
* PostgreSQL rodando localmente.
* IDE (IntelliJ IDEA, Eclipse ou VS Code).

### Configuração do Banco de Dados
1.  Abra o seu **pgAdmin 4**.
2.  Crie um banco de dados chamado `literalura`.
3.  No projeto, navegue até `src/main/resources`.
4.  Crie um arquivo chamado `application.properties` (baseado no `application.properties.example`).
5.  Configure suas credenciais:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    ```

### Executando
1.  Clone o repositório: `git clone https://github.com/Bifaniii/Literalura-challenge.git`
2.  Importe o projeto na sua IDE como um projeto Maven.
3.  Execute a classe `LiteraluraApplication.java`.

---
Desenvolvido por [Guilherme Bifani](https://github.com/Bifaniii)
