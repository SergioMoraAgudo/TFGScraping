package com.scrapingTFG.scrapingTFG.dao.Busqueda;

import com.scrapingTFG.scrapingTFG.models.Busqueda;
import com.scrapingTFG.scrapingTFG.models.Cliente;
import com.scrapingTFG.scrapingTFG.models.Producto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class DaoBusquedaImp implements DaoBusqueda{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Busqueda> obtenerBusquedaDeCliente(Cliente cliente) {
        String query="FROM Busqueda b WHERE b.idcliente =:cliente and b.activo=1";
        List<Busqueda> lista=entityManager.createQuery(query, Busqueda.class)
                .setParameter("cliente",cliente)
                .getResultList();
        if (lista.isEmpty()) return null;
        return lista;
    }

    @Override
    public List<Busqueda> obtenerBusquedaDeProducto(Producto producto) {
        String query="FROM Busqueda b WHERE b.idproducto =:producto and b.activo=1";
        List<Busqueda> lista=entityManager.createQuery(query, Busqueda.class)
                .setParameter("producto",producto)
                .getResultList();
        if (lista.isEmpty()) return null;
        return lista;
    }

    @Override
    public Busqueda obtenerBusquedaEspecifica(Cliente cliente, Producto producto) {
        String query="FROM Busqueda b WHERE b.idproducto =:producto and b.idcliente=:cliente";
        List<Busqueda> lista=entityManager.createQuery(query, Busqueda.class)
                .setParameter("producto",producto)
                .setParameter("cliente",cliente)
                .getResultList();
        if (lista.isEmpty()) return null;
        return lista.get(0);
    }


    @Override
    public void desactivarBusqueda(Cliente cliente, Producto producto) {
        Busqueda busqueda=obtenerBusquedaEspecifica(cliente,producto);

        if (!(busqueda==null||busqueda.getActivo()==0)){
            busqueda.setActivo(0);
            entityManager.merge(busqueda);
            if (obtenerBusquedaDeProducto(producto)==null){
                producto.setActivo(0);
                entityManager.merge(producto);
            }
        }
    }

    @Override
    public void registrarBusqueda(Busqueda busqueda) {
        entityManager.merge(busqueda);
    }

}
