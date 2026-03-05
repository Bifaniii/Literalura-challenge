package com.guilhermebifani.literalura.principal;

import com.guilhermebifani.literalura.model.*;
import com.guilhermebifani.literalura.repository.AutorRepository;
import com.guilhermebifani.literalura.repository.LivroRepository;
import com.guilhermebifani.literalura.service.ConsumoApi;
import com.guilhermebifani.literalura.service.ConverteDados;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
        private Scanner leitura = new Scanner(System.in);
        private ConsumoApi consumoApi = new ConsumoApi();
        private ConverteDados conversor = new ConverteDados();
        private final String URL_BASE = "https://gutendex.com/books/?search=";

        private LivroRepository livroRepository;
        private AutorRepository autorRepository;

        public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
            this.livroRepository = livroRepository;
            this.autorRepository = autorRepository;
        }

        public void exibeMenu() {
            var opcao = -1;
            while (opcao != 0) {
                var menu = """
                        -----------
                        Escolha o número da sua opção:
                        1) buscar livro pelo título
                        2) listar livros registrados
                        3) listar autores registrados
                        4) listar autores vivos em um determinado ano
                        5) listar livros em um determinado idioma
                        0) sair
                        """;

                System.out.println(menu);
                try {
                    opcao = Integer.parseInt(leitura.nextLine());

                    switch (opcao) {
                        case 1 -> buscarLivroPeloTitulo();
                        case 2 -> listarLivrosRegistrados();
                        case 3 -> listarAutoresRegistrados();
                        case 4 -> listarAutoresVivosNoAno();
                        case 5 -> listarLivrosPorIdioma();
                        case 0 -> System.out.println("Saindo...");
                        default -> System.out.println("Opção inválida!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, digite um número válido.");
                }
            }
        }

        private void buscarLivroPeloTitulo() {
            System.out.println("Digite o nome do livro que deseja buscar:");
            var nomeLivro = leitura.nextLine();

            // Verifica se o livro já existe no banco antes de fazer a busca na API
            Optional<Livro> livroExistente = livroRepository.findByTituloIgnoreCase(nomeLivro);
            if (livroExistente.isPresent()) {
                System.out.println("Este livro já está registrado no banco de dados.");
                System.out.println(livroExistente.get());
                return;
            }

            var json = consumoApi.obterDados(URL_BASE + nomeLivro.replace(" ", "%20"));
            DadosResposta dadosResposta = conversor.obterDados(json, DadosResposta.class);

            if (dadosResposta.resultados() != null && !dadosResposta.resultados().isEmpty()) {
                DadosLivro dadosLivro = dadosResposta.resultados().get(0); // Pega o primeiro resultado

                // Verifica/Salva o autor
                DadosAutor dadosAutor = dadosLivro.autores().get(0);
                Autor autor = autorRepository.findByNome(dadosAutor.nome())
                        .orElseGet(() -> autorRepository.save(new Autor(dadosAutor)));

                // Salva o livro
                Livro livro = new Livro(dadosLivro, autor);
                livroRepository.save(livro);

                System.out.println(livro);
            } else {
                System.out.println("Livro não encontrado na API.");
            }
        }

        private void listarLivrosRegistrados() {
            List<Livro> livros = livroRepository.findAll();
            if (livros.isEmpty()) {
                System.out.println("Nenhum livro registrado no banco de dados.");
            } else {
                livros.forEach(System.out::println);
            }
        }

        private void listarAutoresRegistrados() {
            List<Autor> autores = autorRepository.findAll();
            if (autores.isEmpty()) {
                System.out.println("Nenhum autor registrado.");
            } else {
                autores.forEach(System.out::println);
            }
        }

        private void listarAutoresVivosNoAno() {
            System.out.println("Digite o ano que deseja pesquisar:");
            try {
                var ano = Integer.parseInt(leitura.nextLine());
                List<Autor> autores = autorRepository.buscarAutoresVivosNoAno(ano);
                if (autores.isEmpty()) {
                    System.out.println("Nenhum autor vivo encontrado neste ano no banco de dados.");
                } else {
                    autores.forEach(System.out::println);
                }
            } catch (NumberFormatException e) {
                System.out.println("Ano inválido. Digite um número.");
            }
        }

        private void listarLivrosPorIdioma() {
            System.out.println("""
                    Insira o idioma para realizar a busca:
                    es - espanhol
                    en - inglês
                    fr - francês
                    pt - português
                    """);
            var idioma = leitura.nextLine();
            List<Livro> livros = livroRepository.findByIdioma(idioma);

            if (livros.isEmpty()) {
                System.out.println("Não existem livros neste idioma no banco de dados");
            } else {
                livros.forEach(System.out::println);
            }
        }
    }
