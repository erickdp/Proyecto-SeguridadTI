package com.uce.edu.seguridad.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "formulario")
public class Formulario implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_formulario")
    private Long idFormulario;

    @Column(name = "tipo_formulario")
    private String tipoFormulario;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "formulario")
    private List<Pregunta> preguntas;

}
