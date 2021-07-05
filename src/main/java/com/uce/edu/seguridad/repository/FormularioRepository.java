package com.uce.edu.seguridad.repository;

import com.uce.edu.seguridad.models.Formulario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormularioRepository extends JpaRepository<Formulario, Long> {
    Formulario findByTipoFormulario(String tipoFormulario);
}
