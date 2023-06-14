package com.scrapingTFG.scrapingTFG.dao.DataScraping;

import com.scrapingTFG.scrapingTFG.models.Datascraping;
import com.scrapingTFG.scrapingTFG.models.Producto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@Transactional
public class DaoDataScrapingImp implements DaoDataScraping{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Datascraping> getResultados(Producto producto){
        String query="FROM Datascraping d WHERE d.idproducto=:producto";
        List<Datascraping> lista=entityManager.createQuery(query, Datascraping.class)
                .setParameter("producto",producto)
                .getResultList();
        if (lista.isEmpty()) return null;
        return lista; //devuelve la lista
    }

}
