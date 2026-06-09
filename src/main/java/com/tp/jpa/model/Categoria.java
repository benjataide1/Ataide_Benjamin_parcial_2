package com.tp.jpa.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "categorias")
public class Categoria extends Base {

    private String nombre;
    private String descripcion;

    @Builder.Default
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // indica que la relacion es bidireccional y que el lado propietario de la relacion es el atributo "categoria" en la clase Producto
    private List<Producto> productos = new ArrayList<>();
}
