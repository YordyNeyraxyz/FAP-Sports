package fap_sports.integrador.repositories;

import org.springframework.stereotype.*;
import org.springframework.data.jpa.repository.JpaRepository;

import fap_sports.integrador.models.Campeonato;

// Repositorio para la entidad Campeonato, extiende de JpaRepository para
// proporcionar métodos CRUD y consultas básicas
@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long> {
    
}
