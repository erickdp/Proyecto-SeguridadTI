package com.uce.edu.seguridad.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uce.edu.seguridad.models.*;
import com.uce.edu.seguridad.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
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
    // Al agregar una pregunta esta se agrega para todos los participantes con calificacion de 0
    @GetMapping("/agregarPregunta/{idformulario}/{pregunta}")
    public Pregunta agregarPregunta(
            @PathVariable(name = "idformulario") Long idformulario,
            @PathVariable(name = "pregunta") String pregunta) {
        var formularioA = this.formularioService.consultarPorId(idformulario);
        var preguntaA = this.preguntaService.agregarPreguntaAFormulario(pregunta, formularioA);
        this.coworkerService.agreagarNuevaPregunta(preguntaA); // Aqui se agrega la neuva pregunta al usuario con calificacion de 0
        return preguntaA;
    }

//    IMPORTANTE!!
//   YA NO USAR este metodo y usar para calificar las preguntas actualizarPreguntas para calificaciones
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
    2. Cuando se crea un nuevo coworker la calificacion de todas las preguntas es 0
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
    public Coworker crearCoworker(@RequestBody Coworker nuevoCoworker) {
        return this.coworkerService.setearPreguntas(nuevoCoworker, this.preguntaService.listarEntidad());
    }

    /*
    Para actualizar la calificacion de la pregunta se debe de enviar un json como el ejemplo
    siguiente:
    1. Cada objeto es representado con {} y se debe de empezar con [], ademas solo es necesario
    enviar los atributos especificados en el ejemplo, no mas
    [
    {
        "idCoworkerPregunta": 34,
        "calificacion": 3,
        "pregunta": {
            "idPregunta": 5
        }
    },
    {
        "idCoworkerPregunta": 35,
        "calificacion": 7,
        "pregunta": {
            "idPregunta": 6
        }
    }
]
    * */
    @PostMapping("/actualizarPreguntas/{idCoworker}")
    public Coworker actualizarPreguntas(
            @RequestBody String listaPreguntas,
            @PathVariable Long idCoworker
    ) {
        var gson = new Gson();
        Type type = new TypeToken<List<CoworkerPregunta>>(){}.getType();
        List<CoworkerPregunta> preguntasJson = gson.fromJson(listaPreguntas, type);
        return this.coworkerService.actualizarCalificacion(idCoworker, preguntasJson);
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

//    Este metodo permite obtener las preguntas de un determinado Coworker
    @GetMapping("/preguntasCoworker/{idCoworker}")
    public List<CoworkerPregunta> preguntasCoworker(
            @PathVariable(name = "idCoworker") Long id
    ) {
        return this.coworkerService.buscarCowokerPorId(id).getPreguntas();
    }

//    NO TOMAR EN CUENTA
//    Este metodo sera usado luego cuando solo se desee enviar JSON desde java
    @GetMapping("/obtenerJson")
    public HashMap<String, Integer> obtenerJson() {
        var mapa = new HashMap<String, Integer>();
        mapa.put("edad", 22);
        mapa.put("altura", 165);
        return mapa;
    }

//    Metodo que permite obtener el promedio de cada pregunta realizada por todos los participates de un determinado formulario
//    y para una determinada universidad
    @GetMapping("/promedioPreguntas/{idFormulario}/{nombreUniversidad}")
    public HashMap<String, Double> promedioPreguntas(
            @PathVariable(value = "idFormulario") Long id,
            @PathVariable(value = "nombreUniversidad") String nombre
    ) {
        var universidad = this.universidadService.buscarUnieversidadPorNombre(nombre);
        var preguntasFiltradas = this.preguntaService.obtenerPreguntasPorFormulario(id);
        return this.coworkerPreguntaService.promedioPorPreguntaDeFormulario(preguntasFiltradas, universidad.getIdUniversidad());
    }

//    Metodo que permite obtener el numero de participantes de una determinada universidad para algun formulario
    @GetMapping("/participantesPregunta/{idFormulario}/{nombreUniversidad}")
    public HashMap<String, Long> participantesPregunta(
            @PathVariable(value = "idFormulario") Long id,
            @PathVariable(value = "nombreUniversidad") String nombre
    ) {
        var universidad = this.universidadService.buscarUnieversidadPorNombre(nombre);
        var preguntasFiltradas = this.preguntaService.obtenerPreguntasPorFormulario(id);
        return this.coworkerPreguntaService.obtenerParticipantesPorPregunta(preguntasFiltradas, universidad.getIdUniversidad());
    }
}
