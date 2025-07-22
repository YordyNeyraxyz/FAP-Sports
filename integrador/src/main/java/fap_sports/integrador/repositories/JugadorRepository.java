package fap_sports.integrador.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fap_sports.integrador.models.Jugador;

// Repositorio para la entidad Jugador, extiende JpaRepository para
// proporcionar métodos CRUD y consultas básicas
@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long>{
    //Metodo boleano para verificar si el jugador pertenece a un equipo
    boolean existsByEquipoEquId(Long equipoId);
}
