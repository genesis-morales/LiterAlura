package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreIgnoreCase(String nombre);

    @Query("""
        SELECT a FROM Autor a
        WHERE a.fechaNacimiento <= :annio
        AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento >= :annio)
    """)
    List<Autor> autoresVivosEnAnnio(int annio);

}
