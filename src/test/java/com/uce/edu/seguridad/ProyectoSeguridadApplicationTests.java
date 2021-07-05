package com.uce.edu.seguridad;

import com.uce.edu.seguridad.models.*;
import com.uce.edu.seguridad.service.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ProyectoSeguridadApplicationTests {

    @Autowired
    private ProvinciaService provinciaService;
    @Autowired
    private UniversidadService universidadService;
    @Autowired
    private CoworkerService coworkerService;
    @Autowired
    private PreguntaService preguntaService;
    @Autowired
    private CoworkerPreguntaService coworkerPreguntaService;

    @Test
    void notNullService() {
        assertNotNull(this.provinciaService);
    }

    @Test
    @Disabled
    void guardarProvinciaTest() {
        var universidad = new Universidad();
        universidad.setNombreUniversidad("UCE");

        var provinciaEsperada = new Provincia();
        provinciaEsperada.setNombre("Quito");
        provinciaEsperada.setUnivesidades(Collections.singletonList(universidad));

        universidad.setProvincia(provinciaEsperada);

        this.provinciaService.guardar(provinciaEsperada);
        var provinciaReal = this.provinciaService.consultarPorId(2L);

        provinciaEsperada.setIdProvincia(2L);

        assertEquals(provinciaEsperada, provinciaReal);
    }

    @Test
    @Disabled
    void actualizarTest() {

        var provincia = this.provinciaService.consultarPorId(2L);
        assertNotNull(provincia);
        // Se pasa como referencia la instancia
        var universidad = provincia.getUnivesidades().stream()
                .filter(u -> u.getNombreUniversidad().contains("UCE"))
                .findFirst()
                .orElseThrow();

        universidad.setNombreUniversidad("PUCE");
        this.provinciaService.guardar(provincia);
    }

    @Test
    @Disabled
    void testAgregarCalificacion() {
        var coworker = this.coworkerService.consultarPorId(1L);
        assertNotNull(coworker);
        log.info(coworker.getMailInstitucional());
        var pregunta = this.preguntaService.consultarPorId(1L);
        assertNotNull(pregunta);
        log.info(pregunta.getPregunta());

        var coworkerPregunta = new CoworkerPregunta();
        coworkerPregunta.setCoworker(coworker);
        coworkerPregunta.setPregunta(pregunta);
        coworkerPregunta.setCalificacion(1);
        this.coworkerPreguntaService.guardar(coworkerPregunta);

        var coworkerPEsperado = this.coworkerPreguntaService.consultarPorId(1L);
        assertNotNull(coworkerPEsperado);
    }

    @Test
    void testActualizarCalificacion() {
        var coworkerPregunta = this.coworkerPreguntaService.consultarPorId(1L);
        coworkerPregunta.setCalificacion(2);
        this.coworkerPreguntaService.guardar(coworkerPregunta);
    }
}
