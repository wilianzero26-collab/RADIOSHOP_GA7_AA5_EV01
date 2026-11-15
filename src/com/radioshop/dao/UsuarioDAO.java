package com.radioshop.dao;

import com.radioshop.conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UsuarioDAO {

    // Registrar un usuario nuevo
    public boolean registrar(String usuario, String password)
            throws SQLException {

        String sql = "INSERT INTO usuarios (usuario, password_hash) VALUES (?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, usuario);
            consulta.setString(2, generarHash(password));

            return consulta.executeUpdate() > 0;
        }
    }

    // Validar las credenciales de acceso
    public boolean autenticar(String usuario, String password)
            throws SQLException {

        String sql = "SELECT password_hash FROM usuarios WHERE usuario = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, usuario);

            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    String hashGuardado = resultado.getString("password_hash");
                    return hashGuardado.equals(generarHash(password));
                }
            }
        }

        return false;
    }

    // Convertir la contraseña en un resumen SHA-256
    private String generarHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            StringBuilder hexadecimal = new StringBuilder();

            for (byte b : bytes) {
                hexadecimal.append(String.format("%02x", b));
            }

            return hexadecimal.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No se pudo procesar la contraseña", e);
        }
    }
}