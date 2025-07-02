package fap_sports.integrador.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fap_sports.integrador.models.Decada;
import fap_sports.integrador.models.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    List<Equipo> findByDecada(Decada decada);

    // Traer equipos por el parametro -> anioInicio
    @Query("SELECT e FROM Equipo e WHERE e.decada.anioInicio = :anio")
    List<Equipo> findByAnioInicio(@Param("anio") int anio);
}