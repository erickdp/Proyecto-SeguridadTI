package com.uce.edu.seguridad.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "pregunta")
public class Pregunta implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pregunta")
    private Long idPregunta;

    @Column(name = "pregunta", length = 400)
    private String pregunta;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "id_formulario")
    private Formulario formulario;

}
