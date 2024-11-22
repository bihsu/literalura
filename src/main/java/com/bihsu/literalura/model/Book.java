package com.bihsu.literalura.model;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long idApi;
    @Column(unique = true)
    private String title;
    private String languages;
    private Long downloadCount;

    @ManyToMany
    @JoinTable(
        name = "books_personas",
        joinColumns = @JoinColumn(name = "books_id"),
        inverseJoinColumns = @JoinColumn(name="personas_id")
    )
    private List<Persona> personas;

    public Book() {
    }

    public Book(DatosBook datos){
        this.idApi = datos.idApi();
        this.title = datos.title();
        this.personas = datos.authors().stream().map(a->new Persona(a)).collect(Collectors.toList());
        this.languages = datos.languages().get(0).toString();
        this.downloadCount = datos.downloadCount();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getIdApi() {
        return idApi;
    }
    public void setIdApi(Long idApi) {
        this.idApi = idApi;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<Persona> getAuthors() {
        return personas;
    }
    public void setAuthors(List<Persona> authors) {
        this.personas = authors;
    }
    public String getLanguages() {
        return languages;
    }
    public void setLanguages(String languages) {
        this.languages = languages;
    }
    public Long getDownloadCount() {
        return downloadCount;
    }
    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }
}
