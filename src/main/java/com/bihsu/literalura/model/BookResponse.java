package com.bihsu.literalura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown= true)
public record BookResponse(
    Integer count,
    List<DatosBook> results
) {

}
