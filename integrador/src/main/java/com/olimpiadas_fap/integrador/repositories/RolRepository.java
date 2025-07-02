package com.olimpiadas_fap.integrador.repositories;

import com.olimpiadas_fap.integrador.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre);
}