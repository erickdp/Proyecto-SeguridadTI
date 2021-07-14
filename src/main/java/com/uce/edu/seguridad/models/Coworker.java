package com.uce.edu.seguridad.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "id_coworker")
    private List<CoworkerPregunta> preguntas;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "id_universidad")
    @NotNull
    private Universidad universidad;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
