package com.bihsu.literalura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bihsu.literalura.model.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona,Long>{

    boolean existsByName(String name);
    Persona findByName(String nane);

    @Query("SELECT p FROM Persona p WHERE p.deathYear > :anio")
    List<Persona> findByMayorAnio(Integer anio);
}
