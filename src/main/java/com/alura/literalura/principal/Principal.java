package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.DatosRespuesta;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private LibroRepository repository;
    private AutorRepository autorRepository;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.repository = libroRepository;
        this.autorRepository = autorRepository;
    }

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
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombre = teclado.nextLine();

        // 1. Buscar primero en la base de datos
        Optional<Libro> libroExistente =
                repository.findByTituloContainsIgnoreCase(nombre);

        if (libroExistente.isPresent()) {
            System.out.println("El libro ya existe en la base de datos:");
            System.out.println(libroExistente.get());
            return;
        }

        // 2. Si no existe, consumir la API
        var json = consumoApi.obtenerDatos(
                URL_BASE + nombre.replace(" ", "+")
        );

        DatosRespuesta respuesta =
                conversor.obtenerDatos(json, DatosRespuesta.class);

        if (respuesta.resultados().isEmpty()) {
            System.out.println("Libro no encontrado en la API");
            return;
        }

        DatosLibro datos = respuesta.resultados().get(0);

        // 3. Guardar
        Libro libro = new Libro(datos);
        System.out.println(datos);
        repository.save(libro);
    }

    private void librosRegistrados() {
        libros = repository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
            return;
        }

        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(libro -> {
                    System.out.println("----- LIBRO -----");
                    System.out.println("Título: " + libro.getTitulo());

                    System.out.println("Autor(es): " +
                            libro.getAutores().stream()
                                    .map(Autor::getNombre)
                                    .toList()
                    );
                    System.out.println("Idioma(s): " + libro.getIdiomas());
                    System.out.println("Descargas: " + libro.getNumeroDescargas());
                });
    }

    private void autoresRegistrados() {
        autores = autorRepository.findAll();

        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(autor -> {
                    System.out.println("----- AUTOR -----");
                    System.out.println("Nombre: " + autor.getNombre());
                    System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
                    System.out.println("Fecha de fallecimiento: " + autor.getFechaFallecimiento());
                    System.out.println("-----------------");
                });
    }

    private void autoresPorAnnio() {
        System.out.println("Digite el año que deseas buscar:");
        int annio = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autores = autorRepository.autoresVivosEnAnnio(annio);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
            return;
        }

        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(autor -> {
                    System.out.println("----- AUTOR -----");
                    System.out.println("Nombre: " + autor.getNombre());
                    System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
                    System.out.println("Fecha de fallecimiento: " +
                            (autor.getFechaFallecimiento() != null
                                    ? autor.getFechaFallecimiento()
                                    : "Vivo"));
                    System.out.println("-----------------");
                });
    }

    private void librosPorIdioma() {
        System.out.println("""
            Digite el idioma para buscar los libros:
            es - español
            en - inglés
            fr - francés
            pt - portugués
            """);

        String idioma = teclado.nextLine().toLowerCase();

        libros = repository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
            return;
        }

        Set<String> idiomasValidos = Set.of("es", "en", "fr", "pt");
        if (!idiomasValidos.contains(idioma)) {
            System.out.println("Idioma no válido.");
            return;
        }

        libros.forEach(libro -> {
            System.out.println("----- LIBRO -----");
            System.out.println("Título: " + libro.getTitulo());

            String autores = libro.getAutores().stream()
                    .map(Autor::getNombre)
                    .collect(Collectors.joining(", "));
            System.out.println("Autor(es): " + autores);

            String idiomas = libro.getIdiomas().stream()
                    .map(id -> switch (id) {
                        case "es" -> "Español";
                        case "en" -> "Inglés";
                        case "fr" -> "Francés";
                        case "pt" -> "Portugués";
                        default -> id;
                    })
                    .collect(Collectors.joining(", "));
            System.out.println("Idioma(s): " + idiomas);

            System.out.println("Número de descargas: " + libro.getNumeroDescargas());
            System.out.println("-----------------\n");
        });
    }
}
