package com.bihsu.literalura.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bihsu.literalura.model.DatosPersona;
import com.bihsu.literalura.model.Persona;
import com.bihsu.literalura.repository.PersonaRepository;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public List<DatosPersona> listarAutores(){
        List<Persona> personas =  personaRepository.findAll();
        return personas.stream()
            .map(p -> new DatosPersona(p.getBirthYear(), p.getDeathYear(), p.getName(), p.getBooks())).collect(Collectors.toList());
    }

    public List<DatosPersona> buscarPoranio(Integer anio){
        return personaRepository.findByMayorAnio(anio).stream()
                                .map(p -> new DatosPersona(p.getBirthYear(), p.getDeathYear(), p.getName(), p.getBooks())).collect(Collectors.toList());
    }

}
