package com.olimpiadas_fap.integrador.repositories;

import com.olimpiadas_fap.integrador.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}