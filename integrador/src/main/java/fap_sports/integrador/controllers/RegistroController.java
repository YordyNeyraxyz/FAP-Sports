package fap_sports.integrador.controllers;

import fap_sports.integrador.models.Decada;
import fap_sports.integrador.models.Rol;
import fap_sports.integrador.models.Tipo;
import fap_sports.integrador.models.Usuario;
import fap_sports.integrador.services.RolService;
import fap_sports.integrador.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

// Clase controladora para manejar el registro y gestión de usuarios
@Controller
public class RegistroController {

    private final UsuarioService usuarioService; // Servicio para manejar operaciones relacionadas con usuarios
    private final RolService rolService; // Servicio para manejar operaciones relacionadas con roles

    // Constructor para inyección de dependencias
    public RegistroController(UsuarioService usuarioService, RolService rolService) {
        this.usuarioService = usuarioService; // Inicializa el servicio de usuarios
        this.rolService = rolService; // Inicializa el servicio de roles
    }

    // Mostrar formulario de registro con datos necesarios para la vista
    @GetMapping("/registro")
    public String mostrarRegistroForm(Model model) {
        model.addAttribute("usuario", new Usuario()); // Crea un objeto vacío para el formulario
        model.addAttribute("roles", rolService.listarRoles()); // Obtiene el listado de roles disponibles
        model.addAttribute("tipos", usuarioService.getAllTipos()); // Obtiene los tipos de usuario
        model.addAttribute("decadas", usuarioService.getAllDecadas()); // Obtiene las décadas para selección
        model.addAttribute("usuarios", usuarioService.getAllUsuarios()); // Obtiene la lista de usuarios para mostrar
        return "vistas/administrador/usuario/registrarLicenciado"; // Retorna la vista del formulario de registro
    }

    // Procesar el registro de un nuevo usuario
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, @RequestParam Long tipoId, @RequestParam Long decId) {
        try {
            // Obtener y asignar tipo y década seleccionados
            Tipo tipo = usuarioService.getTipoById(tipoId); // Obtiene el tipo de usuario por ID
            Decada decada = usuarioService.getDecadaById(decId); // Obtiene la década por ID
            usuario.setTipo(tipo); // Asigna el tipo al usuario
            usuario.setDecada(decada); // Asigna la década al usuario

            // Si no tiene roles asignados, asignar el rol de invitado por defecto
            if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
                Rol rolInvitado = rolService.obtenerRolPorId(3L); // Obtiene el rol de invitado
                if (rolInvitado != null) {
                    List<Rol> roles = new ArrayList<>(); // Crea una lista para los roles
                    roles.add(rolInvitado); // Añade el rol de invitado
                    usuario.setRoles(roles); // Establece los roles del usuario
                }
            }

            // Registrar usuario a través del servicio
            usuarioService.registrarUsuario(usuario); // Guarda el usuario en la base de datos
            return "redirect:/registro"; // Redirige al formulario de registro

        } catch (Exception e) {
            // En caso de error, mostrar el formulario de registro nuevamente
            return "vistas/administrador/usuario/registrarLicenciado"; // Retorna a la vista de registro
        }
    }

    // Eliminar un usuario por su ID
    @PostMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id); // Elimina el usuario por ID
        return "redirect:/registro"; // Redirige al formulario de registro
    }

    // Mostrar formulario para editar un usuario existente
    @GetMapping("/editarUsuario/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.getUsuarioById(id); // Obtiene el usuario por ID
        model.addAttribute("usuario", usuario); // Agrega el usuario al modelo
        model.addAttribute("tipos", usuarioService.getAllTipos()); // Obtiene los tipos de usuario disponibles
        model.addAttribute("decadas", usuarioService.getAllDecadas()); // Obtiene las décadas disponibles
        // model.addAttribute("roles", usuarioService.getAllRoles()); // Activar si es necesario para editar roles
        return "vistas/administrador/usuario/editarLicenciado"; // Retorna la vista de edición de usuario
    }

    // Procesar la actualización de un usuario
    @PostMapping("/actualizarUsuario")
    public String actualizarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.updateUsuario(usuario); // Actualiza el usuario en la base de datos
        return "redirect:/registro"; // Redirige al formulario de registro
    }
}