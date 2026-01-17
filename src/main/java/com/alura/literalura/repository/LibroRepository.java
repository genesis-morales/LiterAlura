package com.alura.literalura.repository;

import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String nombre);
    List<Libro> findTop10ByOrderByNumeroDescargasDesc();

    @Query("""
    SELECT l FROM Libro l
    JOIN l.idiomas i
    WHERE i = :idioma
    """)
    List<Libro> findByIdioma(@Param("idioma") String idioma);

}
