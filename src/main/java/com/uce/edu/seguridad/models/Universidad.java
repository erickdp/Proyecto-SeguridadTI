package com.uce.edu.seguridad.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "universidad")
public class Universidad implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_universidad")
    private Long idUniversidad;

    @Column(name = "nombre_universidad", unique = true)
    @NotEmpty(message = "Se requiere el nombre de la Universidad")
    private String nombreUniversidad;
}
