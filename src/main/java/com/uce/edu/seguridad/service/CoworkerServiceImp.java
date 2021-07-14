package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Coworker;
import com.uce.edu.seguridad.models.CoworkerPregunta;
import com.uce.edu.seguridad.models.Pregunta;
import com.uce.edu.seguridad.models.Usuario;
import com.uce.edu.seguridad.repository.CoworkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class CoworkerServiceImp implements CoworkerService {

    @Autowired
    private CoworkerRepository coworkerRepository;

    @Override
    @Transactional
    public Coworker guardar(Coworker entidad) {
        return this.coworkerRepository.save(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Coworker consultarPorId(Long id) {
        return this.coworkerRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Coworker> listarEntidad() {
        return this.coworkerRepository.findAll();
    }

    @Override
    @Transactional
    public Coworker actualizarEntidad(Coworker entidad) {
        return this.coworkerRepository.save(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Coworker> buscarPorUnivesidad(Long id) {
        return this.coworkerRepository.findScoreUniversidad(id);
    }

    @Override
    public Coworker calificarPregunta(Coworker coworker, Pregunta pregunta, Integer calificacion) {
        CoworkerPregunta coworkerPregunta = new CoworkerPregunta();
        coworkerPregunta.setPregunta(pregunta);
        coworkerPregunta.setCalificacion(calificacion);
        coworker.getPreguntas().add(coworkerPregunta);
        this.actualizarEntidad(coworker);
        return coworker;
    }

    @Override
    public void agreagarNuevaPregunta(Pregunta pregunta) {
        List<Coworker> coworkers = this.listarEntidad();
        Coworker coworker;
        CoworkerPregunta coworkerPregunta;
        for (Coworker value : coworkers) {
            coworker = value;
            coworkerPregunta = new CoworkerPregunta();
            coworkerPregunta.setPregunta(pregunta);
            coworkerPregunta.setCalificacion(0);
            coworker.getPreguntas().add(coworkerPregunta);
            this.guardar(coworker);
        }
    }

    // Se necesita mayor cohesion y bajo acoplamiento en estos metodos de servicio
    @Override
    public Coworker setearPreguntas(Coworker coworker, List<Pregunta> preguntaList) {
        List listaPreguntas = new ArrayList<CoworkerPregunta>();
        Coworker coworkerA = coworker;
        preguntaList.forEach(pregunta -> {
            CoworkerPregunta coworkerPregunta = new CoworkerPregunta();
            coworkerPregunta.setPregunta(pregunta);
            coworkerPregunta.setCalificacion(0);
            listaPreguntas.add(coworkerPregunta);
        });
        coworkerA.setPreguntas(listaPreguntas);
        return this.guardar(coworkerA);
    }

    @Override
    @Transactional(readOnly = true)
    public Coworker buscarCowokerPorId(Long id) {
        return this.coworkerRepository.findById(id).orElse(null);
    }

    // Se necesita mayor cohesion y bajo acoplamiento en estos metodos de servicio
    @Override
    public Coworker actualizarCalificacion(
            Long idCoworker,
            List<CoworkerPregunta> preguntas) {
        Coworker coworker = this.buscarCowokerPorId(idCoworker);
        List<CoworkerPregunta> listaAntigua = coworker.getPreguntas();

        for (CoworkerPregunta pN : preguntas) {
            CoworkerPregunta preCalificadaNueva = pN;
            if (listaAntigua.contains(preCalificadaNueva)) {
                CoworkerPregunta p = listaAntigua.get(listaAntigua.indexOf(preCalificadaNueva));
                p.setCalificacion(preCalificadaNueva.getCalificacion());
            }
        }

        return this.actualizarEntidad(coworker);
    }

    @Transactional(readOnly = true)
    @Override
    public Coworker buscarCoworkerPorNombreUsuario(String nombreUsuario) {
        return this.coworkerRepository.findByNombreUsuario(nombreUsuario);
    }

    @Override
    @Transactional
    public void eliminarEntidad() {
        List<Coworker> usuarios = this.listarEntidad();
        Set<Long> ids = new HashSet<>();
        usuarios.forEach(u -> ids.add(u.getIdCoworker()));
        this.coworkerRepository.deleteAllByIds(ids);
    }
}
