package com.uce.edu.seguridad.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

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
    @NotEmpty
    @Size(max = 100, message = "El nombre del formulario es demasiado largo")
    private String tipoFormulario;

}
