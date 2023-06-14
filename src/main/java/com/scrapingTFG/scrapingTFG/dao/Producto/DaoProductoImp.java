package com.scrapingTFG.scrapingTFG.dao.Producto;

import com.scrapingTFG.scrapingTFG.models.Producto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class DaoProductoImp implements DaoProducto{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Producto getProducto(Integer id) {
        String query="FROM Producto p WHERE p.id=:id";
        List<Producto> lista=entityManager.createQuery(query,Producto.class)
                .setParameter("id",id)
                .getResultList();
        if (lista.isEmpty()) return null;
        return lista.get(0); //devuelve el primer valor de la lista
    }


    @Override
    public Producto getProductoActivo(Integer id) {
        Producto producto=getProducto(id);
        if(!(producto==null||producto.getActivo()==1)){
            return null;
        }
        return producto;
    }

    @Override
    public void activarProducto(Integer id) {
        Producto producto=getProducto(id);
        if (!(producto==null||producto.getActivo()==1)){
            entityManager.merge(producto);
        }
    }
    @Override
    public void desactivarProducto(Integer id) {
        Producto producto=getProducto(id);
        if (!(producto==null||producto.getActivo()==0)){
            entityManager.merge(producto);
        }
    }

    @Override
    public void registrarProducto(Producto producto) {
        entityManager.persist(producto);
        entityManager.flush();
        entityManager.refresh(producto);
    }

    @Override
    public Producto buscarPorNombre(String productoBuscado) {
        String query="FROM Producto p WHERE p.nombre=:nombre";
        List<Producto> lista=entityManager.createQuery(query,Producto.class)
                .setParameter("nombre",productoBuscado)
                .getResultList();
        if (lista.isEmpty()) return null;
        return lista.get(0);
    }

}
