package com.uce.edu.seguridad.models;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = "coworker_pregunta")
public class CoworkerPregunta implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coworker_pregunta")
    private Long idCoworkerPregunta;

    private Integer calificacion;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_coworker", referencedColumnName = "id_coworker")
    private Coworker coworker;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_pregunta", referencedColumnName = "id_pregunta")
    private Pregunta pregunta;

}
