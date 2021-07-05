package com.uce.edu.seguridad.controllers;

import com.uce.edu.seguridad.models.Usuario;
import com.uce.edu.seguridad.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seguridad")
public class ControladoPrincipal {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/iniciarSession/{usuario}/{password}")
    public Usuario iniciarSession(
            @PathVariable(name = "usuario") String usuario,
            @PathVariable(name = "password") String password) {
        var usuarioRepo = this.usuarioService.buscarPorUsuarioYContrasena(usuario, password);
        return usuarioRepo;
    }

}
