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
public class DatascrapingId implements Serializable {
    private static final long serialVersionUID = 7963436079548862595L;
    @Column(name = "idproducto", nullable = false)
    private Integer idproducto;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "fecha", nullable = false, length = 50)
    private String fecha;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DatascrapingId entity = (DatascrapingId) o;
        return Objects.equals(this.fecha, entity.fecha) &&
                Objects.equals(this.idproducto, entity.idproducto) &&
                Objects.equals(this.url, entity.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, idproducto,url);
    }

}