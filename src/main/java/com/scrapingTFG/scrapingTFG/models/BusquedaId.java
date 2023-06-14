package com.scrapingTFG.scrapingTFG.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class BusquedaId implements Serializable {
    private static final long serialVersionUID = 6898957693199915927L;
    @Column(name = "idcliente", nullable = false)
    private Integer idcliente;

    @Column(name = "idproducto", nullable = false)
    private Integer idproducto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BusquedaId entity = (BusquedaId) o;
        return Objects.equals(this.idcliente, entity.idcliente) &&
                Objects.equals(this.idproducto, entity.idproducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcliente, idproducto);
    }

}