package com.uce.edu.seguridad.controllers;

import com.uce.edu.seguridad.models.*;
import com.uce.edu.seguridad.service.*;
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

    @Autowired
    private ProvinciaService provinciaService;

    // Metodo para iniciar session, necesita el nombre del usuario y su pass
    // Para sabe si es coworker u admin utilizar el atributo de Rol.tipoRol
    @GetMapping("/iniciarSession/{usuario}/{password}")
    public Usuario iniciarSession(
            @PathVariable(name = "usuario") String usuario,
            @PathVariable(name = "password") String password) {
        var usuarioRepo = this.usuarioService.buscarPorUsuarioYContrasena(usuario, password);
        return usuarioRepo;
    }

    // Metodo que devuelve las preguntas y al tipo de formulario a donde pertenece
    @GetMapping("/obtenerPreguntas")
    public List<Pregunta> obtenerPreguntas() {
        return this.preguntaService.listarEntidad();
    }

    // Metodo para agregar pregunta mediante el id del formulario y la pregunta en si
    //    Para enviar una pregunta con espacios hacer %20
//    el id del formulario es tipo numero y la pregunta es cadena
    @GetMapping("/agregarPregunta/{idformulario}/{pregunta}")
    public Pregunta agregarPregunta(
            @PathVariable(name = "idformulario") Long idformulario,
            @PathVariable(name = "pregunta") String pregunta) {
        var formularioA = this.formularioService.consultarPorId(idformulario);
        var preguntaA = new Pregunta();
        preguntaA.setPregunta(pregunta);
        preguntaA.setFormulario(formularioA);
        this.preguntaService.guardar(preguntaA);
        return preguntaA;
    }

    // Con este metodo se permite agregar calificacion a la pregunta mediante el id de la pregunta y la calificacion en si
    // idpregunta, calificacion enviar un entero (si se puede de tipo Long) y el coworker en json
    @PostMapping("/calificarPregunta/{idpregunta}/{calificacion}")
    public Coworker calificarPregunta(
            @RequestBody Coworker coworker, // Se necesita enviar un JSON de tipo Coworker, basta con el parametro del mail
            @PathVariable(name = "idpregunta") Long idPregunta,
            @PathVariable(name = "calificacion") Integer calificacion
    ) {
        var coworkerA = this.coworkerService.buscarPorMailInstitucional(coworker.getMailInstitucional());
        var preguntas = coworkerA.getPreguntas();
        var preguntaA = this.preguntaService.consultarPorId(idPregunta);

        var coworkerPregunta = new CoworkerPregunta();
        coworkerPregunta.setPregunta(preguntaA);
        coworkerPregunta.setCalificacion(calificacion);

        preguntas.add(coworkerPregunta);

        this.coworkerService.guardar(coworkerA);

        return coworkerA;
    }

    @GetMapping("/obtenerUniversidades")
    public List<Provincia> obtenerUniversidades() {
        return this.provinciaService.listarEntidad();
    }

}
