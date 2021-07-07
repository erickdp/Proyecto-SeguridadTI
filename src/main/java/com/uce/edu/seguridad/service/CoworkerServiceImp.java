package com.uce.edu.seguridad.service;

import com.uce.edu.seguridad.models.Coworker;
import com.uce.edu.seguridad.models.CoworkerPregunta;
import com.uce.edu.seguridad.models.Pregunta;
import com.uce.edu.seguridad.repository.CoworkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
    @Transactional
    public Coworker buscarPorMailInstitucional(String mailInstitucional) {
        return this.coworkerRepository.findByMailInstitucional(mailInstitucional);
    }

    @Override
    @Transactional
    public List<Coworker> buscarPorUnivesidad(Long id) {
        return this.coworkerRepository.findScoreUniversidad(id);
    }

    @Override
    public Coworker calificarPregunta(Coworker coworker, Pregunta pregunta, Integer calificacion) {
        var coworkerPregunta = new CoworkerPregunta();
        coworkerPregunta.setPregunta(pregunta);
        coworkerPregunta.setCalificacion(calificacion);
        coworker.getPreguntas().add(coworkerPregunta);
        this.actualizarEntidad(coworker);
        return coworker;
    }

    @Override
    public void agreagarNuevaPregunta(Pregunta pregunta) {
        var coworkers = this.listarEntidad();
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

    @Override
    public Coworker setearPreguntas(Coworker coworker, List<Pregunta> preguntaList) {
        var listaPreguntas = new ArrayList<CoworkerPregunta>();
        var coworkerA = coworker;
        preguntaList.forEach(pregunta -> {
            var coworkerPregunta = new CoworkerPregunta();
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

    @Override
    public Coworker actualizarCalificacion(
            Long idCoworker,
            List<CoworkerPregunta> preguntas) {
        var coworker = this.buscarCowokerPorId(idCoworker);
        var listaAntigua = coworker.getPreguntas();

        for (CoworkerPregunta pN : preguntas) {
            var preCalificadaNueva = pN;
            if (listaAntigua.contains(preCalificadaNueva)) {
                var p = listaAntigua.get(listaAntigua.indexOf(preCalificadaNueva));
                p.setCalificacion(preCalificadaNueva.getCalificacion());
            }
        }

//        listaAntigua.forEach(p -> {
//            var preCalificada = p;
//            preguntas.forEach(pa -> {
//                var preCalificadaNueva = pa;
//                if(preCalificada.equals(preCalificadaNueva)) {
//                    preCalificada.setCalificacion(preCalificadaNueva.getCalificacion());
//                }
//            });
//        });
        return this.actualizarEntidad(coworker);
    }
}
