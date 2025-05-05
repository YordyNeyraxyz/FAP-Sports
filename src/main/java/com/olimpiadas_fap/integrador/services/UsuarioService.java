package com.olimpiadas_fap.integrador.services;

import com.olimpiadas_fap.integrador.models.Rol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.olimpiadas_fap.integrador.models.Usuario;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registrarUsuario(Usuario usuario) {
        String contraseniaEncriptada = passwordEncoder.encode(usuario.getContrasenia());
        usuario.setContrasenia(contraseniaEncriptada);

        logger.info("Encriptando contraseña para el usuario: {}", usuario.getEmail());

        try {
            String sql = "INSERT INTO usuarios (nombre_usuario, apellido_usuario, contrasenia_usuario, correo_usuario, telefono_usuario, dni_usuario) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
            logger.info("Ejecutando consulta SQL: {}", sql);
            logger.info("Valores: nombre={}, apellido={}, contrasenia={}, email={}, telefono={}, dni={}",
                    usuario.getNombre(), usuario.getApellido(), usuario.getContrasenia(), usuario.getEmail(), usuario.getTelefono(), usuario.getDni());

            @SuppressWarnings("deprecation")
            Long usuarioId = jdbcTemplate.queryForObject(sql, new Object[]{
                            usuario.getNombre(),
                            usuario.getApellido(),
                            usuario.getContrasenia(),
                            usuario.getEmail(),
                            usuario.getTelefono(),
                            usuario.getDni()
                    },
                    Long.class);

            List<Rol> roles = usuario.getRoles();
            if (roles != null && !roles.isEmpty()) {
                for (Rol rol : roles) {
                    String sqlUsuariosRoles = "INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (?, ?)";
                    logger.info("Ejecutando consulta SQL: {}", sqlUsuariosRoles);
                    logger.info("Valores: usuario_id={}, rol_id={}", usuarioId, rol.getId());
                    jdbcTemplate.update(sqlUsuariosRoles, usuarioId, rol.getId());
                }
            }

            logger.info("Usuario registrado con éxito: {}", usuario.getEmail());

        } catch (DataAccessException e) {
            logger.error("Error al insertar el usuario", e);
            logger.error("Error al insertar el usuario: " + e.getMessage(), e); 
            System.err.println("Error al insertar el usuario: " + e.getMessage());
            throw new RuntimeException("Error al registrar el usuario", e);
        }
    }
}