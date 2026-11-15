package com.radioshop.principal;

import com.radioshop.conexion.ConexionBD;
import java.sql.Connection;

public class PruebaConexion {

    public static void main(String[] args) {

        // Verificar conexión con MySQL
        try (Connection conexion = ConexionBD.conectar()) {

            System.out.println("==============================");
            System.out.println("CONEXION EXITOSA CON RADIOSHOP");
            System.out.println("Base de datos: radioshop_usuarios");
            System.out.println("==============================");

        } catch (Exception e) {

            System.out.println("ERROR DE CONEXION: " + e.getMessage());
        }
    }
}