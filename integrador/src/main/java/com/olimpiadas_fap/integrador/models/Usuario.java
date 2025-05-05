package com.olimpiadas_fap.integrador.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_usuario", nullable = false)
    private String nombre;

    @Column(name = "apellido_usuario", nullable = false)
    private String apellido;

    @Column(name = "contrasenia_usuario", nullable = false)
    private String contrasenia;

    @Column(name = "correo_usuario", unique = true, nullable = false)
    private String email;

    @Column(name = "telefono_usuario", unique = true, nullable = false)
    private String telefono;

    @Column(name = "dni_usuario", unique = true, nullable = false)
    private String dni;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String contrasenia, String email, String telefono, String dni, List<Rol> roles) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasenia = contrasenia;
        this.email = email;
        this.telefono = telefono;
        this.dni = dni;
        this.roles = roles;
    }

    public Usuario(String nombre, String apellido, String contrasenia, String email, String telefono, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasenia = contrasenia;
        this.email = email;
        this.telefono = telefono;
        this.dni = dni;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", dni='" + dni + '\'' +
                ", roles=" + roles +
                '}';
    }
}