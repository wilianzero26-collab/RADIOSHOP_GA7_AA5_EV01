package com.radioshop.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Dirección de la base de datos
    private static final String URL =
        "jdbc:mysql://localhost:3306/radioshop_usuarios?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    // Usuario de MySQL
    private static final String USUARIO = "root";

    // Contraseña local de MySQL
    private static final String CLAVE = System.getenv("RADIOSHOP_DB_PASSWORD");

    // Método para conectar con MySQL
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}