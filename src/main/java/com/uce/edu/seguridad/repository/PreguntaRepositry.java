package com.uce.edu.seguridad.repository;

import com.uce.edu.seguridad.models.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreguntaRepositry extends JpaRepository<Pregunta, Long> {
}
