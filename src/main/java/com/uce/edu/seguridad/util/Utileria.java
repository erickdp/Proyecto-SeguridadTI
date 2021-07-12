package com.uce.edu.seguridad.util;

import com.uce.edu.seguridad.models.Coworker;
import com.uce.edu.seguridad.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Utileria {

    public static List<Usuario> filtrarUsuario(List<Coworker> coworkers) {
        var usuarios = new ArrayList<Usuario>();
        coworkers.forEach(c -> usuarios.add(c.getUsuario()));
        return usuarios;
    }
}
