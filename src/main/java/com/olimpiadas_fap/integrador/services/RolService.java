package com.olimpiadas_fap.integrador.services;

import com.olimpiadas_fap.integrador.models.Rol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    private static final Logger logger = LoggerFactory.getLogger(RolService.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RolService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Rol> listarRoles() {
        String sql = "SELECT id, nombre_rol FROM roles";
        logger.info("Ejecutando consulta SQL: {}", sql);
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Rol rol = new Rol();
            rol.setId(rs.getLong("id"));
            rol.setNombre(rs.getString("nombre_rol"));
            return rol;
        });
    }

    @SuppressWarnings("deprecation")
    public Rol obtenerRolPorId(Long id) {
        String sql = "SELECT id, nombre_rol FROM roles WHERE id = ?";
        logger.info("Ejecutando consulta SQL: {}", sql);
        try {
            
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                Rol rol = new Rol();
                rol.setId(rs.getLong("id"));
                rol.setNombre(rs.getString("nombre_rol"));
                return rol;
            });
        } catch (Exception e) {
            logger.error("Error al obtener el rol con ID: {}", id, e);
            return null;
        }
    }
}