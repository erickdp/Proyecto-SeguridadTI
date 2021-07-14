package com.uce.edu.seguridad.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coworker_pregunta")
public class CoworkerPregunta implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coworker_pregunta")
    private Long idCoworkerPregunta;

    @NotNull
    private Integer calificacion;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "id_pregunta")
    private Pregunta pregunta;

    @Override
    public boolean equals(Object o) {
        CoworkerPregunta that = (CoworkerPregunta) o;
        return this.getIdCoworkerPregunta().equals(that.getIdCoworkerPregunta())
                &&
                this.getPregunta().getIdPregunta().equals(that.getPregunta().getIdPregunta());
    }
}
