package com.scrapingTFG.scrapingTFG.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "datascraping")
public class Datascraping {
    @EmbeddedId
    private DatascrapingId id;

    @MapsId("idproducto")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idproducto", nullable = false)
    //@JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Producto idproducto;

    @Column(name = "nombreproducto", nullable = false, length = 30)
    private String nombreproducto;

    @Column(name = "tiendanombre", nullable = false, length = 30)
    private String tiendanombre;

    @Column(name = "precio", nullable = false, length = 10)
    private String precio;

    @Column(name = "valoraciones", length = 10)
    private String valoraciones;

    @Column(name = "envio", length = 10)
    private String envio;

}