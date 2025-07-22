package fap_sports.integrador.services;

import fap_sports.integrador.models.Partido;
import fap_sports.integrador.models.Campeonato;
import fap_sports.integrador.models.CampeonatoEquipo;
import fap_sports.integrador.models.Equipo;
import fap_sports.integrador.repositories.PartidoRepository;
import fap_sports.integrador.repositories.CampeonatoEquipoRepository;
import fap_sports.integrador.repositories.CampeonatoRepository;
//import fap_sports.integrador.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

// Clase de servicio para manejar la lógica de los partidos
@Service
public class PartidoService {

    @Autowired
    private PartidoRepository partidoRepository; // Repositorio para acceder a la entidad Partido

//    @Autowired
//    private EquipoRepository equipoRepository; // Repositorio para acceder a la entidad Equipo

    @Autowired
    private CampeonatoRepository campeonatoRepository; // Repositorio para acceder a la entidad Campeonato

    @Autowired
    private CampeonatoEquipoRepository campeonatoEquipoRepository; // Repositorio para manejar la relación entre Campeonatos y Equipos
   
    // Método para realizar el sorteo de partidos por campeonato
    public void realizarSorteoPorCampeonato(Long campeonatoId, List<Equipo> equipos) {
        if (equipos == null || equipos.size() < 2 || equipos.size() % 2 != 0) {
            throw new IllegalArgumentException("Debes seleccionar una cantidad par de equipos (mínimo 2).");
        }

        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
            .orElseThrow(() -> new RuntimeException("Campeonato no encontrado."));

        // Guardar equipos asignados al campeonato
        for (Equipo equipo : equipos) {
            CampeonatoEquipo ce = new CampeonatoEquipo();
            ce.setCampeonato(campeonato);
            ce.setEquipo(equipo);
            campeonatoEquipoRepository.save(ce);
        }
   
        Collections.shuffle(equipos); // Mezclar los equipos

        // Crear partidos de dos en dos
        for (int i = 0; i < equipos.size(); i += 2) {
            Partido partido = new Partido();
            partido.setEquipoLocal(equipos.get(i)); // Asignar equipo local
            partido.setEquipoVisitante(equipos.get(i + 1)); // Asignar equipo visitante
            partido.setCampeonato(campeonato); // Asignar campeonato
            partidoRepository.save(partido); // Guardar el partido
        }
    }

    // Crear un nuevo partido
    public Partido crearPartido(Partido partido) {
        return partidoRepository.save(partido); // Guardar el partido en la base de datos
    }

    // Listar todos los partidos
    public List<Partido> listarPartidos() {
        return partidoRepository.findAll(); // Obtener todos los partidos
    }

    // Obtener un partido por su ID
    public Optional<Partido> obtenerPartidoPorId(Long id) {
        return partidoRepository.findById(id); // Buscar el partido por ID
    }

    // Actualizar un partido existente
    public Partido actualizarPartido(Long id, Partido partidoActualizado) {
        return partidoRepository.findById(id).map(partidoExistente -> {
            partidoExistente.setEquipoLocal(partidoActualizado.getEquipoLocal()); // Actualizar equipo local
            partidoExistente.setEquipoVisitante(partidoActualizado.getEquipoVisitante()); // Actualizar equipo visitante
            partidoExistente.setParFecha(partidoActualizado.getParFecha()); // Actualizar fecha
            partidoExistente.setParHora(partidoActualizado.getParHora()); // Actualizar hora
            return partidoRepository.save(partidoExistente); // Guardar los cambios
        }).orElseThrow(() -> new IllegalArgumentException("Partido no encontrado con ID: " + id)); // Manejo de excepción si no se encuentra
    }

    // Eliminar un partido por su ID
    public void eliminarPartido(Long id) {
        partidoRepository.deleteById(id); // Eliminar el partido
    }
    
    // Obtener los últimos partidos
    public List<Partido> obtenerUltimosPartidos() {
        return partidoRepository.findTop3ByParFechaIsNotNullAndParHoraIsNotNullOrderByParFechaDescParHoraDesc(); // Obtener los últimos tres partidos
    }

    // Obtener partidos por ID de campeonato
    public List<Partido> obtenerPartidosPorCampeonatoId(Long campeonatoId) {
        return partidoRepository.findByCampeonatoCamId(campeonatoId); // Buscar partidos por ID del campeonato
    }
}