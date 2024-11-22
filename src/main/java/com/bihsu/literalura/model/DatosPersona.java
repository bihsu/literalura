package com.bihsu.literalura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosPersona(
    @JsonAlias("birth_year") Integer birthYear,
    @JsonAlias("death_year") Integer deathYear,
    String name,
    List<Book> books
) {

}
