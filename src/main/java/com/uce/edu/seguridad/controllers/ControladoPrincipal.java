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

    @Autowired
    private UniversidadService universidadService;

    @Autowired
    private CoworkerPreguntaService coworkerPreguntaService;

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
        return this.preguntaService.agregarPreguntaAFormulario(pregunta, formularioA);
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
        var preguntaA = this.preguntaService.consultarPorId(idPregunta);

        return this.coworkerService.calificarPregunta(coworkerA, preguntaA, calificacion);
    }

    //    Metodo para obtener (solo) todas las universidades registradas
    @GetMapping("/obtenerUniversidades")
    public List<Provincia> obtenerUniversidades() {
        return this.provinciaService.listarEntidad();
    }

    //    Con este metodo se va a poder obtener al todos los coworkerss y las preguntas que hayan echo con su calificacion
//    para poder ser tratadas mediante graficos
    @GetMapping("/calificionesPreguntas")
    public List<Coworker> calificionesPreguntas() {
        return this.coworkerService.listarEntidad();
    }

    /*
    Para crear un nuevo coworker se lo debe de enviar mediante formato JSON y solo es necesario
    enviar las llaves foraneas a las entidades a relacionar, no todos sus atributos, como rol o uni
    1. Para coworkers se le debe enviar el idRol = 2, no otro
    Ej:
    {
    "mailInstitucional": "pedrop@uce.com",
    "usuario": {
        "nombreUsuario": "pedrop",
        "password": "123",
        "nombres": "Pedro",
        "apellidos": "Pascal",
        "rol": {
            "idRol": 2
        }
    },
    "universidad": {
        "idUniversidad": 1
    }
    }
    * */
    @PostMapping("/crearCoworker")
    public void crearCoworker(@RequestBody Coworker nuevoCoworker) {
        this.coworkerService.guardar(nuevoCoworker);
    }

    /*
    Para crear un administrador se debe de enviar el solo los atributos del objeto
    usuario junto con el rol en especifico el idRol = 1 en formato JSON
    Ej:
    {
    "nombreUsuario": "suarez",
    "password": "123",
    "nombres": "Freddy",
    "apellidos": "Suarez",
    "rol": {
        "idRol": 1
    }
    }
    * */
    @PostMapping("/crearAdministador")
    public void crearAdministador(@RequestBody Usuario nuevoAdministrador) {
        this.usuarioService.guardar(nuevoAdministrador);
    }

    //    Permite obtener los resultados de los formularios de univeridades en especifico
    @GetMapping("/scorePorUniversidad/{IndentificadorU}")
    public List<Coworker> scorePorUniversidad(
            @PathVariable(name = "IndentificadorU") Long id
    ) {
        return this.coworkerService.buscarPorUnivesidad(id);
    }

}
