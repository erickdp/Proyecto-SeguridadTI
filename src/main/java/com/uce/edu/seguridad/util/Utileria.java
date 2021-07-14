package com.uce.edu.seguridad.util;

import com.uce.edu.seguridad.models.Coworker;
import com.uce.edu.seguridad.models.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utileria {

    public static List<Usuario> filtrarUsuario(List<Coworker> coworkers) {
        List usuarios = new ArrayList<Usuario>();
        coworkers.forEach(c -> usuarios.add(c.getUsuario()));
        return usuarios;
    }

    public static String generarUsuario(Usuario usuario) {
        Usuario usuarioA = usuario;
        String nombre = usuarioA.getNombres().toUpperCase();
        char[] letra = new char[2];
        // Obtengo la primero y ultima letra del nombre
        letra[0] = nombre.charAt(0);
        letra[1] = nombre.charAt(nombre.length() - 1);

        Random r = new Random();
        int numero1 = r.nextInt(50) + 1; // Numero entre 1 y 50
        int numero2 = r.nextInt(50) + 1;
        StringBuilder usuarioC = new StringBuilder();
        // Lo concateno con el apellido
        usuarioC.append(letra[0]).append(usuarioA.getApellidos()).append(letra[1]).append(numero1 += numero2);
        return usuarioC.toString();
    }

    public static Usuario agregarNumero(Usuario usuario) {
        Usuario us = usuario;
        String nombreA = us.getNombreUsuario();
        nombreA += nombreA + 1;
        us.setNombreUsuario(nombreA);
        return us;
    }

    public static String generarContrasena() {
        Random r = new Random();
        StringBuilder pass = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            pass.append(r.nextInt(10) + 1);
        }
        for (int i = 0; i < 3; i++) {
            pass.append((char) (r.nextInt(26) + 'A'));
        }
        return pass.toString();
    }
}
