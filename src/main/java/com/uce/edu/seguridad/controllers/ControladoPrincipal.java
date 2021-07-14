package com.uce.edu.seguridad.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uce.edu.seguridad.models.*;
import com.uce.edu.seguridad.service.*;
import com.uce.edu.seguridad.util.Utileria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
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
    private UniversidadService universidadService;

    @Autowired
    private CoworkerPreguntaService coworkerPreguntaService;

    // Metodo para iniciar session, necesita el nombre del usuario y su pass
    // Para sabe si es coworker u admin utilizar el atributo de Rol.tipoRol OK
    @GetMapping("/iniciarSession/{usuario}/{password}")
    public Usuario iniciarSession(
            @PathVariable(name = "usuario") String usuario,
            @PathVariable(name = "password") String password) {
        Usuario usuarioRepo = this.usuarioService.buscarPorUsuarioYContrasena(usuario, password);
        return usuarioRepo;
    }

    // Metodo que devuelve las preguntas y al tipo de formulario a donde pertenece OK
    @GetMapping("/obtenerPreguntas")
    public List<Pregunta> obtenerPreguntas() {
        return this.preguntaService.listarEntidad();
    }

    // Metodo para agregar pregunta mediante el id del formulario y la pregunta en si
    //    Para enviar una pregunta con espacios hacer %20
//    el id del formulario es tipo numero y la pregunta es cadena
    // Al agregar una pregunta esta se agrega para todos los participantes con calificacion de 0 OK
    @GetMapping("/agregarPregunta/{idformulario}/{pregunta}")
    public Pregunta agregarPregunta(
            @PathVariable(name = "idformulario") Long idformulario,
            @PathVariable(name = "pregunta") String pregunta) {
        Formulario formularioA = this.formularioService.consultarPorId(idformulario);
        Pregunta preguntaA = this.preguntaService.agregarPreguntaAFormulario(pregunta, formularioA);
        this.coworkerService.agreagarNuevaPregunta(preguntaA); // Aqui se agrega la neuva pregunta al usuario con calificacion de 0
        return preguntaA;
    }

//    IMPORTANTE!!
//   YA NO USAR este metodo y usar para calificar las preguntas actualizarPreguntas para calificaciones
    // Con este metodo se permite agregar calificacion a la pregunta mediante el id de la pregunta y la calificacion en si
    // idpregunta, calificacion enviar un entero (si se puede de tipo Long) y el coworker en json
//    @PostMapping("/calificarPregunta/{idpregunta}/{calificacion}")
//    public Coworker calificarPregunta(
//            @RequestBody Coworker coworker, // Se necesita enviar un JSON de tipo Coworker, basta con el parametro del mail
//            @PathVariable(name = "idpregunta") Long idPregunta,
//            @PathVariable(name = "calificacion") Integer calificacion
//    ) {
//        var coworkerA = this.coworkerService.buscarPorMailInstitucional(coworker.getMailInstitucional());
//        var preguntaA = this.preguntaService.consultarPorId(idPregunta);
//
//        return this.coworkerService.calificarPregunta(coworkerA, preguntaA, calificacion);
//    }

    //    Metodo para obtener (solo) todas las universidades registradas OK
    @GetMapping("/obtenerUniversidades")
    public List<Universidad> obtenerUniversidades() {
        return this.universidadService.listarEntidad();
    }

    //    Con este metodo se va a poder obtener al todos los coworkerss y las preguntas que hayan echo con su calificacion
//    para poder ser tratadas mediante graficos OK
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
        "nombres": "Pedro",
        "apellidos": "Pascal",
        "rol": {
            "idRol": 2
        }
    },
    "universidad": {
        "idUniversidad": 1
    }
    } OK
    * */
    @PostMapping("/crearCoworker")
    public ResponseEntity<?> crearCoworker(@RequestBody Coworker nuevoCoworker) {
        Coworker nuevoCoworkerS = nuevoCoworker;
        String nombreUsuarioA = Utileria.generarUsuario(nuevoCoworkerS.getUsuario());
        String contrasenaA = Utileria.generarContrasena();
        nuevoCoworkerS.getUsuario().setNombreUsuario(nombreUsuarioA);
        nuevoCoworkerS.getUsuario().setPassword(contrasenaA);
        Coworker coworkerS = this.coworkerService.setearPreguntas(nuevoCoworkerS, this.preguntaService.listarEntidad());
        return new ResponseEntity<Coworker>(coworkerS, HttpStatus.OK); // Codigo 200 o sea OK
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
    @PostMapping("/actualizarPreguntas/{nombreUsuario}")
    public Coworker actualizarPreguntas(
            @RequestBody String listaPreguntas,
            @PathVariable String nombreUsuario
    ) {
        Coworker coworker = this.coworkerService.buscarCoworkerPorNombreUsuario(nombreUsuario);
        Gson gson = new Gson();
        Type type = new TypeToken<List<CoworkerPregunta>>() {
        }.getType();
        List<CoworkerPregunta> preguntasJson = gson.fromJson(listaPreguntas, type);
        return this.coworkerService.actualizarCalificacion(coworker.getIdCoworker(), preguntasJson);
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
    } OK
    * */
    @PostMapping("/crearAdministador")
    public void crearAdministador(@RequestBody Usuario nuevoAdministrador) {
        this.usuarioService.guardar(nuevoAdministrador);
    }

    //    Permite obtener los resultados de los formularios de univeridades en especifico OK
    @GetMapping("/scorePorUniversidad/{nombreUniversidad}")
    public List<Coworker> scorePorUniversidad(
            @PathVariable(name = "nombreUniversidad") String universidad
    ) {
        Universidad universidadO = this.universidadService.buscarUnieversidadPorNombre(universidad);
        return this.coworkerService.buscarPorUnivesidad(universidadO.getIdUniversidad());
    }

    //    Este metodo permite obtener las preguntas de un determinado Coworker OK
    @GetMapping("/preguntasCoworker/{nombreUsuario}")
    public List<CoworkerPregunta> preguntasCoworker(
            @PathVariable(name = "nombreUsuario") String usuario
    ) {
        Coworker coworker = this.coworkerService.buscarCoworkerPorNombreUsuario(usuario);
        return this.coworkerService.buscarCowokerPorId(coworker.getIdCoworker()).getPreguntas();
    }

    //    NO TOMAR EN CUENTA
//    Este metodo sera usado luego cuando solo se desee enviar JSON desde java
//    @GetMapping("/obtenerJson")
//    public HashMap<String, Integer> obtenerJson() {
//        Map mapa = new HashMap<String, Integer>();
//        mapa.put("edad", 22);
//        mapa.put("altura", 165);
//        return mapa;
//    }

    //    Metodo que permite obtener el promedio de cada pregunta realizada por todos los participates de un determinado formulario
//    y para una determinada universidad PROBAR MAS
    @GetMapping("/promedioPreguntas/{tipoFormulario}/{nombreUniversidad}")
    public LinkedHashMap<String, String> promedioPreguntas(
            @PathVariable(value = "tipoFormulario") String formulario,
            @PathVariable(value = "nombreUniversidad") String nombre
    ) {
        Universidad universidad = this.universidadService.buscarUnieversidadPorNombre(nombre);
        Formulario formularioO = this.formularioService.buscarPorNombreFormulario(formulario);
        List<Pregunta> preguntasFiltradas = this.preguntaService.obtenerPreguntasPorFormulario(formularioO.getIdFormulario());
        return this.coworkerPreguntaService.promedioPorPreguntaDeFormulario(preguntasFiltradas, universidad.getIdUniversidad());
    }

    @GetMapping("/soloPromedioPreguntas/{tipoFormulario}/{nombreUniversidad}")
    public LinkedHashMap<String, Double> soloPromedioPreguntas(
            @PathVariable(value = "tipoFormulario") String formulario,
            @PathVariable(value = "nombreUniversidad") String nombre
    ) {
        Universidad universidad = this.universidadService.buscarUnieversidadPorNombre(nombre);
        Formulario formularioO = this.formularioService.buscarPorNombreFormulario(formulario);
        List<Pregunta> preguntasFiltradas = this.preguntaService.obtenerPreguntasPorFormulario(formularioO.getIdFormulario());
        return this.coworkerPreguntaService.soloPromedioPorPreguntaDeFormulario(preguntasFiltradas, universidad.getIdUniversidad());
    }

    @GetMapping("/promedioYpreguntas/{tipoFormulario}/{nombreUniversidad}")
    public HashMap<String, HashMap<String, Double>> promedioYpreguntas(
            @PathVariable(value = "tipoFormulario") String formulario,
            @PathVariable(value = "nombreUniversidad") String nombre
    ) {
        Universidad universidad = this.universidadService.buscarUnieversidadPorNombre(nombre);
        Formulario formularioO = this.formularioService.buscarPorNombreFormulario(formulario);
        List<Pregunta> preguntasFiltradas = this.preguntaService.obtenerPreguntasPorFormulario(formularioO.getIdFormulario());
        return this.coworkerPreguntaService.promedioYPreguntaDeFormulario(preguntasFiltradas, universidad.getIdUniversidad());
    }

    //    Metodo que permite obtener el numero de participantes de una determinada universidad para algun formulario
    @GetMapping("/participantesPregunta/{tipoFormulario}/{nombreUniversidad}")
    public HashMap<String, Long> participantesPregunta(
            @PathVariable(value = "tipoFormulario") String formulario,
            @PathVariable(value = "nombreUniversidad") String nombre
    ) {
        Universidad universidad = this.universidadService.buscarUnieversidadPorNombre(nombre);
        Formulario formularioO = this.formularioService.buscarPorNombreFormulario(formulario);
        List<Pregunta> preguntasFiltradas = this.preguntaService.obtenerPreguntasPorFormulario(formularioO.getIdFormulario());
        return this.coworkerPreguntaService.obtenerParticipantesPorPregunta(preguntasFiltradas, universidad.getIdUniversidad());
    }

    // Metodo que permite obtener a los participantes en funcion de su universidad
    @GetMapping("/obtenerUsuarioPorUniversidad/{nombreUniversidad}")
    public List<Usuario> obtenerCoworkersPorUniversidad(@PathVariable String nombreUniversidad) {
        Universidad universidadad = this.universidadService.buscarUnieversidadPorNombre(nombreUniversidad);
        List<Coworker> coworkers = this.coworkerService.buscarPorUnivesidad(universidadad.getIdUniversidad());
        return Utileria.filtrarUsuario(coworkers);
    }

    //    Metodo para agregar una nueva universidad, en el caso de que no esten todas
    @GetMapping("/agregarUniversidad/{nombreUniversidad}")
    public Universidad guardarUniversidad(@PathVariable String nombreUniversidad) {
        Universidad u = new Universidad();
        u.setNombreUniversidad(nombreUniversidad);
        return this.universidadService.guardar(u);
    }

    @DeleteMapping("/resetearTodo")
    public HashMap<String, String> resetearTodo() {
        this.coworkerPreguntaService.elimianarEntidad();
        this.coworkerService.eliminarEntidad();
        this.usuarioService.eliminarEntidad();
        HashMap<String, String> res = new HashMap<>();
        res.put("mensaje", "limpiado");
        return res;
    }

    @GetMapping("/obtenerUCoworker/{nombreUsuario}")
    public LinkedHashMap<String, String> obtenerUsuario(@PathVariable String nombreUsuario) {
        LinkedHashMap<String, String> mapa = new LinkedHashMap<>();
        Usuario u = this.usuarioService.buscarPorNombreUsuario(nombreUsuario);
        Coworker c = this.coworkerService.obtenerUniversidad(u.getIdUsuario());
        mapa.put("universidad", c.getUniversidad().getNombreUniversidad());
        mapa.put("idUniversidad", String.valueOf(c.getUniversidad().getIdUniversidad()));
        return mapa;
    }
}
