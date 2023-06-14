package com.scrapingTFG.scrapingTFG.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;


@Entity
@Getter
@Setter
@ToString
@Table(name = "busqueda")
@RequiredArgsConstructor
public class Busqueda {
    @EmbeddedId
    private BusquedaId id;

    @MapsId("idcliente")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idcliente", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cliente idcliente;

    @MapsId("idproducto")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idproducto", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Producto idproducto;

    @Column(name = "activo", nullable = false)
    private Integer activo;
}