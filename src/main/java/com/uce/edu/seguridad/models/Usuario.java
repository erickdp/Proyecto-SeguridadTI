package com.uce.edu.seguridad.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = "usuario") // En postgresql no se puede usar el nombre de user para la clase
public class Usuario implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre_usuario", unique = true)
    private String nombreUsuario;

    private String password;

    @NotEmpty
    @Size(max = 15)
    private String nombres;

    @NotEmpty
    @Size(max = 15)
    private String apellidos;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "id_rol", updatable = false, nullable = false)
    private Rol rol;
}
