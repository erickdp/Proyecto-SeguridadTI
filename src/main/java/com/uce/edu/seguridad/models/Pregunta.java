package com.uce.edu.seguridad.models;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "pregunta")
public class Pregunta implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pregunta")
    private Long idPregunta;

    private String pregunta;

    @OneToMany(mappedBy = "pregunta")
    private List<CoworkerPregunta> coworkers;

    @ManyToOne
    @JoinColumn(name = "id_formulario")
    private Formulario formulario;

}
