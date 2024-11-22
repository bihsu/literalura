package com.bihsu.literalura.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bihsu.literalura.model.Book;
import com.bihsu.literalura.model.DatosBook;
import com.bihsu.literalura.model.DatosPersona;
import com.bihsu.literalura.model.Persona;
import com.bihsu.literalura.repository.BookRepository;
import com.bihsu.literalura.repository.PersonaRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public List<DatosBook> listarLibros(){
        List<Book> book = repository.findAll();
        return book.stream()
                .map(b -> new DatosBook(b.getIdApi(),
                    b.getTitle(),
                    b.getAuthors().stream().map(a->new DatosPersona(a.getBirthYear(), a.getDeathYear(), a.getName(),null)).collect(Collectors.toList()),
                    List.of(b.getLanguages()),
                    b.getDownloadCount()))
                .collect(Collectors.toList());
    }

    @Transactional
    public DatosBook guardarLibro(DatosBook datos){
        Book book = new Book(datos);

        //valiodamos y guardamos a persona
        List<Persona> personas = guardarPersonas(book.getAuthors());
        book.setAuthors(personas);
        book = repository.save(book);
        return new DatosBook(book.getIdApi(),
                            book.getTitle(),
                            book.getAuthors().stream().map(d->new DatosPersona(d.getBirthYear(), d.getDeathYear(), d.getName(),null)).collect(Collectors.toList()),
                            List.of(book.getLanguages()), 
                            book.getDownloadCount());
    }

    @Transactional
    private List<Persona> guardarPersonas(List<Persona> personas){
        List<Persona> perList = new ArrayList<>();
        for (Persona persona : personas) {
            if(personaRepository.existsByName(persona.getName())){
                Persona per = personaRepository.findByName(persona.getName());
                perList.add(per);
            }
            else{
                Persona per = personaRepository.save(persona);
            perList.add(per);
            }
        }
        return perList;
    }

    @Transactional
    public boolean libroExist(DatosBook datosBook){
        boolean existe = false;
        existe = repository.existsByTitle(datosBook.title());
        return existe;
    }

    @Transactional
    public DatosBook buscarPorTitulo(String titulo){
        Book book = repository.findByTitle(titulo);
        return new DatosBook(
            book.getIdApi(),
            book.getTitle(),
            book.getAuthors().stream().map(a->new DatosPersona(a.getBirthYear(), a.getDeathYear(), a.getName(),null)).collect(Collectors.toList()),
            List.of(book.getLanguages()),
            book.getDownloadCount());
    }
}
