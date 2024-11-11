package com.apicalendario.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "calendario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Calendario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( nullable = false)
    private LocalDate fecha;
    @ManyToOne()
    @JoinColumn(name = "idtipo", nullable = false)
    private Tipo tipo;
    @Column(length = 100,name = "Descripcion")
    private String description;
}
