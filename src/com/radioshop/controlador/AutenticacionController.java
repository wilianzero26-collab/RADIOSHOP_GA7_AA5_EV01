package com.radioshop.controlador;

import com.radioshop.dao.UsuarioDAO;
import java.sql.SQLException;

public class AutenticacionController {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Registrar un usuario
    public String registrar(String usuario, String password) {

        if (usuario == null || usuario.isBlank()
                || password == null || password.isBlank()) {
            return "ERROR: usuario y contraseña son obligatorios";
        }

        try {
            boolean registrado = usuarioDAO.registrar(usuario, password);

            if (registrado) {
                return "REGISTRO EXITOSO";
            }

            return "NO SE PUDO REGISTRAR EL USUARIO";

        } catch (SQLException e) {
    e.printStackTrace();
    return "ERROR DE REGISTRO";
}
    }

    // Iniciar sesión
    public String iniciarSesion(String usuario, String password) {

        if (usuario == null || usuario.isBlank()
                || password == null || password.isBlank()) {
            return "ERROR: usuario y contraseña son obligatorios";
        }

        try {
            if (usuarioDAO.autenticar(usuario, password)) {
                return "AUTENTICACION EXITOSA";
            }

            return "ERROR: CREDENCIALES INCORRECTAS";

        } catch (SQLException e) {
            return "ERROR DE AUTENTICACION";
        }
    }
}