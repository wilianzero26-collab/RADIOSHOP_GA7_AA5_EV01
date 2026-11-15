package com.radioshop.modelo;

public class Usuario {

    // Datos básicos del usuario
    private int id;
    private String usuario;
    private String password;

    // Constructor
    public Usuario(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    // Obtener el identificador
    public int getId() {
        return id;
    }

    // Obtener el nombre de usuario
    public String getUsuario() {
        return usuario;
    }

    // Obtener la contraseña
    public String getPassword() {
        return password;
    }

    // Modificar el identificador
    public void setId(int id) {
        this.id = id;
    }
}