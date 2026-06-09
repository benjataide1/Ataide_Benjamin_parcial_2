package com.tp.jpa.model;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private Boolean eliminado = false;

    private LocalDateTime createAt;

    //Se usa para automatizar tareas repetitivas
    @PrePersist // sirve para ejecutar un metodo de manera automatica justo antes de que un objeto se guarde por primera vez en la BD
    protected void onCreate(){
        this.createAt = LocalDateTime.now();
        this.eliminado = false;
    }
}
