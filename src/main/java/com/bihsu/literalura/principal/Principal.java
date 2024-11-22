package com.bihsu.literalura.principal;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.bihsu.literalura.model.Book;
import com.bihsu.literalura.model.BookResponse;
import com.bihsu.literalura.model.DatosBook;
import com.bihsu.literalura.model.DatosPersona;
import com.bihsu.literalura.service.BookService;
import com.bihsu.literalura.service.ConsumoAPI;
import com.bihsu.literalura.service.ConvierteDatos;
import com.bihsu.literalura.service.PersonaService;

public class Principal {

    private final BookService bookService;
    private final PersonaService personaService;

    public Principal(BookService bookService, PersonaService personaService){
        this.bookService = bookService;
        this.personaService = personaService;
    }

    private Scanner teclado = new Scanner(System.in);
    private String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();

    public void mostrarMenu(){
        var opcion = -1;
        var menu = """
                1 - Busca libro por título
                2 - Listar libros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos en un determinado año
                5 - Listar libros por idioma
                0 - Salir
                """;

        System.out.println("\n************ LITERALURA *****************\n");
        System.out.println("---------Elige una opción---------\n");
        System.out.println(menu);
        opcion = teclado.nextInt();
        teclado.nextLine();

        switch (opcion) {
            case 1:
                buscarLibrosPorTitulo();
                break;
            case 2:
                listarLibros();
                break;
            case 3:
                listarAutores();
                break;
            case 4:
                listarAutoresVivos();
                break;
            case 5:
                buscarlibrosPorIdioma();
                break;
            case 0:
                System.out.println("Cerrando la aplicación");
                break;
            default:
            System.out.println("Escibe una opción correcta");
                break;
        }
    }

    private void buscarlibrosPorIdioma(){
        menuIdioma();
        String idioma = teclado.nextLine();
        List<DatosBook> books = bookService.listarLibros().stream()
                .filter(b -> b.languages().contains(idioma))
                .collect(Collectors.toList());
        if(books.size() == 0){
            System.out.println("No se encotraron libros en ese idioma");
        }else{
            books.stream().forEach(Principal::printBook);
        }
        mostrarMenu();
    }

    private void listarAutoresVivos(){
        System.out.println("Ingrese año:");
        String anio = teclado.nextLine();
        while(esNumero(anio)==false){
            System.out.println("no es una año válido, intente de nuevo");
            anio=teclado.nextLine();
        }
        //buscamos los vivos
        personaService.buscarPoranio(Integer.valueOf(anio)).stream()
                                .forEach(Principal::printAuthor);
        mostrarMenu();
    }

    private void listarLibros(){
        List<DatosBook> book = bookService.listarLibros();
        book.stream()
            .forEach(Principal::printBook);
        mostrarMenu();
    }

    private void listarAutores(){
        List<DatosPersona> list = personaService.listarAutores();
        list.stream()
            .forEach(Principal::printAuthor);
        mostrarMenu();
    }

    private Set<DatosBook> getLibros(){
        System.out.println("Escribe el título del libro que quieres buscar:");
        var titulo = teclado.nextLine();
        String json = consumoApi.obtenerDatos(URL_BASE+"?search="+titulo.replace(" ", "%20"));
        BookResponse bookResponse = convierteDatos.obtenerDatos(json, BookResponse.class);
        Set<DatosBook> list = bookResponse.results().stream()
                .filter(r->r.title().toUpperCase().contains(titulo.toUpperCase()))
                .collect(Collectors.toSet());
        return list;
    }

    private DatosBook getLibroById(int id){
        String json = consumoApi.obtenerDatos(URL_BASE+"?ids="+id);
        BookResponse response = convierteDatos.obtenerDatos(json, BookResponse.class);
        return response.results().get(0);
    }

    private void buscarLibrosPorTitulo(){
        Set<DatosBook> list = getLibros();
        if(list.size() == 0){
            System.out.println("==============NO ENCONTRAMOS EL LIBRO==============");
            mostrarMenu();
        }
        System.out.println("===Encontramos "+list.size()+" coincidencias, elige una escribiendo su ID");
        System.out.println("ID\tTÍTULO");
        list.stream().forEach(r->{
            System.out.println(r.idApi()+"\t"+r.title());
        });
        System.out.println("Ingresa el ID del libro o -1 para ir al menú");
        int opcion = teclado.nextInt();

        if(opcion == -1){
            mostrarMenu();
        }

        while (comprobarId(list, opcion) == false) {
            System.out.println("El ID que elegiste no está en la lista, elige otro o presiona -1 para salir");
            opcion = teclado.nextInt();
            if(opcion == -1){
                System.out.println("Saliendo del While");
                break;
            }
        }
        if(opcion == -1){
            mostrarMenu();
        }
        //buscar por id y guardar
        DatosBook book = getLibroById(opcion);

        //validamos si existe
        if(bookService.libroExist(book)){
            System.out.println("====El libro ya existe en la base de datos=====");
            DatosBook datosBook = bookService.buscarPorTitulo(book.title());
            printBook(datosBook);
            mostrarMenu();
        }
        else{
            //guardar los datos del libro
            var res = bookService.guardarLibro(book);
            printBook(res);
            teclado.nextLine();
            mostrarMenu();
        }
    }
    private Boolean comprobarId(Set<DatosBook> list, int id){
        Boolean isPresent = false;
        for(DatosBook item : list){
            if(item.idApi() == id){
                isPresent = true;
            }
        }
        return isPresent;
    }

    private static void printBook(DatosBook book){
        var impresion = """
                ----------------------
                Título: %s
                Autor: %s
                Idioma: %s
                Número de descargar: %d
                """.formatted(book.title(),printAuthors(book.authors()),book.languages().get(0).toString(),book.downloadCount());
        System.out.println(impresion);
    }
    private static String printAuthors(List<DatosPersona> personas){
        StringBuilder autores = new StringBuilder();
        for (DatosPersona persona : personas) {
            String[] autor = persona.name().split(",");
            autores.append(autor[0]+", "+autor[1]);
            if(personas.size() > 1){
                autores.append("/");
            }
        }
        return autores.toString();
    }

    private static void printAuthor(DatosPersona persona){
        var print = """
                Autor: %s
                Fecha de nacimiento: %s
                Fecha de Fallecimiento: %s
                Libros: %s
                """.formatted(persona.name(),persona.birthYear(),persona.deathYear(),persona.books().stream().map(Book::getTitle).collect(Collectors.joining(",")));
        System.out.println(print);
    }

    private static boolean esNumero(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

    private void menuIdioma(){
        var menu = """
                =============Elige un idioma
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués
                """;
        System.out.println(menu);
    }

}
