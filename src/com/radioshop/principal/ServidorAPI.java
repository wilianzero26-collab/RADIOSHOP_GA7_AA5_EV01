
package com.radioshop.principal;

import com.radioshop.controlador.AutenticacionController;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class ServidorAPI {

    private static final AutenticacionController controlador =
            new AutenticacionController();

    public static void main(String[] args) throws IOException {

        // Crear servidor en el puerto 8080
        HttpServer servidor = HttpServer.create(
                new InetSocketAddress(8080), 0);

        // Servicio para registrar usuarios
        servidor.createContext("/registro", intercambio -> {
            procesarSolicitud(intercambio, true);
        });

        // Servicio para iniciar sesión
        servidor.createContext("/login", intercambio -> {
            procesarSolicitud(intercambio, false);
        });

        servidor.start();

        System.out.println("RADIOSHOP API INICIADA");
        System.out.println("Servidor: http://localhost:8080");
        System.out.println("Registro: http://localhost:8080/registro");
        System.out.println("Login: http://localhost:8080/login");
    }

    // Procesar solicitudes de registro e inicio de sesión
    private static void procesarSolicitud(
            HttpExchange intercambio, boolean esRegistro)
            throws IOException {

        if (!intercambio.getRequestMethod().equalsIgnoreCase("POST")) {
            responder(intercambio, 405,
                    "{\"mensaje\":\"Metodo no permitido. Use POST\"}");
            return;
        }

        // Leer los datos enviados por el cliente
        String cuerpo = new String(
                intercambio.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8);

        Map<String, String> datos = new HashMap<>();

        for (String campo : cuerpo.split("&")) {
            String[] partes = campo.split("=", 2);

            if (partes.length == 2) {
                String clave = URLDecoder.decode(
                        partes[0], StandardCharsets.UTF_8);
                String valor = URLDecoder.decode(
                        partes[1], StandardCharsets.UTF_8);

                datos.put(clave, valor);
            }
        }

        String usuario = datos.get("usuario");
        String password = datos.get("password");

        // Ejecutar la operación solicitada
        String resultado;

        if (esRegistro) {
            resultado = controlador.registrar(usuario, password);
        } else {
            resultado = controlador.iniciarSesion(usuario, password);
        }

        // Enviar respuesta al cliente
        if (resultado.contains("EXITOSO")) {
            responder(intercambio, 200,
                    "{\"mensaje\":\"" + resultado + "\"}");
        } else {
            responder(intercambio, 401,
                    "{\"mensaje\":\"" + resultado + "\"}");
        }
    }

    // Enviar respuesta HTTP en formato JSON
    private static void responder(
            HttpExchange intercambio, int codigo, String respuesta)
            throws IOException {

        intercambio.getResponseHeaders().set(
                "Content-Type", "application/json; charset=UTF-8");

        byte[] datos = respuesta.getBytes(StandardCharsets.UTF_8);

        intercambio.sendResponseHeaders(codigo, datos.length);

        try (OutputStream salida = intercambio.getResponseBody()) {
            salida.write(datos);
        }
    }
}
