package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer  fechaNacimiento;
    private Integer  fechaFallecimiento;

    @ManyToMany(mappedBy = "autores")
    private List<Libro> libros;

    public Autor() {}

    public Autor(DatosAutor a) {
        this.nombre = a.nombre();
        this.fechaNacimiento = Integer.valueOf(a.fechaNacimiento() != null
                ? a.fechaNacimiento().toString()
                : "Desconocido");

        this.fechaFallecimiento = Integer.valueOf(a.fechaFallecimiento() != null
                ? a.fechaFallecimiento().toString()
                : "Desconocido");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "\n----- AUTOR -----\n" +
                "Nombre: " + nombre + "\n" +
                "Fecha de nacimiento: " + fechaNacimiento + "\n" +
                "Fecha de fallecimiento: " + fechaFallecimiento + "\n" +
                "-----------------\n";
    }
}
