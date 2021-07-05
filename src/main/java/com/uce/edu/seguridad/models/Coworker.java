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
@Table(name = "coworker")
public class Coworker implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coworker")
    private Long idCoworker;

    @Column(name = "mail")
    private String mailInstitucional;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "coworker")
    private List<CoworkerPregunta> preguntas;

    @ManyToOne
    @JoinColumn(name = "id_universidad")
    private Universidad universidad;
}
