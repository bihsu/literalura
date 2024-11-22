package com.bihsu.literalura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBook(
    @JsonAlias("id") Long idApi,
    String title,
    List<DatosPersona> authors,
    List<String> languages,
    @JsonAlias("download_count") Long downloadCount
) {

}
