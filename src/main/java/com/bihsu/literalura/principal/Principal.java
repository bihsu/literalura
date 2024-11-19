package com.bihsu.literalura.principal;

import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.bihsu.literalura.model.BookList;
import com.bihsu.literalura.model.BookResponse;
import com.bihsu.literalura.service.ConsumoAPI;
import com.bihsu.literalura.service.ConvierteDatos;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private String URL_BASE = "https://gutendex.com/books/?search=";
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

        System.out.println("\n************ BIENVENIDO A LITERALURA*****************\n");
        System.out.println("---------Elige una opción---------\n");
        System.out.println(menu);
        opcion = teclado.nextInt();
        teclado.nextLine();

        switch (opcion) {
            case 1:
                buscarLibrosPorTitulo();
                break;
            case 2:
                System.out.println("presionaste la opción 2");
                break;
            case 3:
                System.out.println("presionaste la opción 3");
                break;
            case 4:
                System.out.println("presionaste la opción 4");
                break;
            case 5:
                System.out.println("presionaste la opción 5");
                break;
            case 0:
                System.out.println("Cerrando la aplicación");
                break;
            default:
                break;
        }
    }

    private Set<BookList> getLibros(){
        System.out.println("Escribe el título del libro que quieres buscar:");
        var titulo = teclado.nextLine();
        String json = consumoApi.obtenerDatos(URL_BASE+titulo.replace(" ", "%20"));
        BookResponse bookResponse = convierteDatos.obtenerDatos(json, BookResponse.class);
        Set<BookList> list = bookResponse.results().stream()
                .filter(r->r.title().toUpperCase().contains(titulo.toUpperCase()))
                .collect(Collectors.toSet());
        return list;
    } 

    private void buscarLibrosPorTitulo(){
        Set<BookList> list = getLibros();
        System.out.println("===Encontramos "+list.size()+" coincidencias, elige una escribiendo su ID");
        System.out.println("ID\tTÍTULO");
        list.stream().forEach(r->{
            System.out.println(r.id()+"\t"+r.title());
        });
        System.out.println("Ingresa el ID del libro o -1 para ir al menú");
        int opcion = teclado.nextInt();
        if(opcion == -1){
            this.mostrarMenu();
        }
        while (comprobarId(list, opcion) == false) {
            System.out.println("ID no encontrado, pruebe de nuevo");
            opcion = teclado.nextInt();
        }
        System.out.println("escogiste el id: "+opcion);
        
    }
    private Boolean comprobarId(Set<BookList> list, int id){
        Boolean isPresent = false;
        for(BookList item : list){
            if(item.id() == id){
                isPresent = true;
            }
        }
        return isPresent;
    }

}
