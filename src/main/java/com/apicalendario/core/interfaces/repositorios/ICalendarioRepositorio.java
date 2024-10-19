package com.apicalendario.core.interfaces.repositorios;

import com.apicalendario.dominio.Calendario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ICalendarioRepositorio extends JpaRepository<Calendario,Long> {

    @Query("SELECT c FROM Calendario c WHERE YEAR(c.fecha) = :anio")
    List<Calendario> findByFechaYear(@Param("anio") String anio);
}
