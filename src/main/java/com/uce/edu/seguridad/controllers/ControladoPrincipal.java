package com.uce.edu.seguridad.controllers;

import com.uce.edu.seguridad.models.*;
import com.uce.edu.seguridad.service.CoworkerService;
import com.uce.edu.seguridad.service.FormularioService;
import com.uce.edu.seguridad.service.PreguntaService;
import com.uce.edu.seguridad.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seguridad")
public class ControladoPrincipal {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FormularioService formularioService;

    @Autowired
    private PreguntaService preguntaService;

    @Autowired
    private CoworkerService coworkerService;

    @GetMapping("/iniciarSession/{usuario}/{password}")
    public Usuario iniciarSession(
            @PathVariable(name = "usuario") String usuario,
            @PathVariable(name = "password") String password) {
        var usuarioRepo = this.usuarioService.buscarPorUsuarioYContrasena(usuario, password);
        return usuarioRepo;
    }

    @GetMapping("/agregarPregunta/{formulario}/{pregunta}")
    public Pregunta agregarPregunta(
            @PathVariable(name = "formulario") String formulario,
            @PathVariable(name = "pregunta") String pregunta) {
        var formularioA = this.formularioService.buscarPorNombreFormulario(formulario);
        var preguntaA = new Pregunta();
        preguntaA.setPregunta(pregunta);
        preguntaA.setFormulario(formularioA);
        this.preguntaService.guardar(preguntaA);
        return preguntaA;
    }

    @PostMapping("/calificarPregunta/{pregunta}/{calificacion}")
    public Coworker calificarPregunta(
            @RequestBody Coworker coworker,
            @PathVariable(name = "pregunta") String pregunta,
            @PathVariable(name = "calificacion") Integer calificacion
    ) {
        var coworkerA = this.coworkerService.buscarPorMailInstitucional(coworker.getMailInstitucional());
        var preguntas = coworkerA.getPreguntas();
        var preguntaA = this.preguntaService.consultarPorId(2L);

        var coworkerPregunta = new CoworkerPregunta();
        coworkerPregunta.setPregunta(preguntaA);
        coworkerPregunta.setCalificacion(calificacion);

        preguntas.add(coworkerPregunta);

        this.coworkerService.guardar(coworkerA);

        return coworkerA;
    }

}
