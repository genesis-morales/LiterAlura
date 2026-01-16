package com.alura.literalura.principal;

import com.alura.literalura.repository.LibroRepository;

import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/";
    private LibroRepository repository;

    public Principal(LibroRepository libroRepository) {this.repository = libroRepository;}

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos por año
                    5 - Listar libros por idiomas              
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLbroPorTitulo();
                    break;
                case 2:
                    librosRegistrados();
                    break;
                case 3:
                    autoresRegistrados();
                    break;
                case 4:
                    autoresPorAnnio();
                    break;
                case 5:
                    librosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void buscarLbroPorTitulo() {
    }

    private void librosRegistrados() {
    }
    private void autoresRegistrados() {
    }
    private void autoresPorAnnio() {
    }
    private void librosPorIdioma() {
        System.out.println("""
                Digite el idioma para buscar los libros:
                es - español
                en - inglés
                fr - francés
                pt - portugués
                """);
        var opcion = teclado.nextInt();
        teclado.nextLine();
    }
}
