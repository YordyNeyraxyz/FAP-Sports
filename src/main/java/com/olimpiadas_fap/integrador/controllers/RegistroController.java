package com.olimpiadas_fap.integrador.controllers;

import com.olimpiadas_fap.integrador.models.Rol;
import com.olimpiadas_fap.integrador.models.Usuario;
import com.olimpiadas_fap.integrador.services.RolService;
import com.olimpiadas_fap.integrador.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.ArrayList;

@Controller
public class RegistroController {

    private static final Logger logger = LoggerFactory.getLogger(RegistroController.class);

    private final UsuarioService usuarioService;
    private final RolService rolService;

    @Autowired
    public RegistroController(UsuarioService usuarioService, RolService rolService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @GetMapping("/registro")
    public String mostrarRegistroForm(Model model) {
        List<Rol> roles = rolService.listarRoles();
        model.addAttribute("roles", roles); 
        return "vistas/registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido,
            @RequestParam("contrasenia") String contrasenia,
            @RequestParam("email") String email,
            @RequestParam("telefono") String telefono,
            @RequestParam("dni") String dni,
            @RequestParam(value = "roles", required = false) List<Long> rolesIds,
            Model model) {
        try {
            logger.info("Recibiendo datos del formulario: nombre={}, apellido={}, email={}, telefono={}, dni={}, roles={}", nombre, apellido, email, telefono, dni, rolesIds);

            Usuario nuevoUsuario = new Usuario(nombre, apellido, contrasenia, email, telefono, dni);

            List<Rol> roles = new ArrayList<>();
            if (rolesIds != null && !rolesIds.isEmpty()) {
                for (Long rolId : rolesIds) {
                    Rol rol = rolService.obtenerRolPorId(rolId);
                    if (rol != null) {
                        roles.add(rol);
                    } else {
                        logger.warn("No se encontró el rol con ID: {}", rolId);
                    }
                }
            }
            nuevoUsuario.setRoles(roles); 

            logger.info("Intentando registrar usuario: {}", nuevoUsuario); 

            usuarioService.registrarUsuario(nuevoUsuario);
            model.addAttribute("mensaje", "Usuario registrado con éxito: " + nombre);
            return "vistas/exito";
        } catch (Exception e) {
            logger.error("Error al registrar el usuario", e);
            logger.error("Error al registrar el usuario: " + e.getMessage(), e); 
            model.addAttribute("error", "Error al registrar el usuario: " + e.getMessage());
            return "vistas/registro";
        }
    }
}