package fap_sports.integrador.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;

// Clase que representa la entidad Partido en la base de datos
@Entity
@Table(name = "partidos") // Especifica el nombre de la tabla en la base de datos
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de IDs autoincrementales
    @Column(name = "par_id") // Columna que representa el ID del partido
    private Long parId;

    // Fecha del partido
    @Column(name = "par_fecha") // Columna para la fecha del partido
    private LocalDate parFecha;

    @Column(name = "par_hora") // Columna para la hora del partido
    private LocalTime parHora;

    @ManyToOne // Relación muchos a uno con la entidad Equipo
    @JoinColumn(name = "equipo_local_id") // Columna que representa el equipo local
    private Equipo equipoLocal;

    @ManyToOne // Relación muchos a uno con la entidad Equipo
    @JoinColumn(name = "equipo_visitante_id") // Columna que representa el equipo visitante
    private Equipo equipoVisitante;

    @ManyToOne // Relación muchos a uno con la entidad Campeonato
    @JoinColumn(name = "cam_id") // Columna que representa el campeonato al que pertenece el partido
    private Campeonato campeonato; 


    // Métodos get y set
    
    public Long getParId() {
        return parId;
    }

    public void setParId(Long parId) {
        this.parId = parId;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public LocalDate getParFecha() {
        return parFecha;
    }

    public void setParFecha(LocalDate parFecha) {
        this.parFecha = parFecha;
    }

    public LocalTime getParHora() {
        return parHora;
    }

    public void setParHora(LocalTime parHora) {
        this.parHora = parHora;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }
}