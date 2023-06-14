package com.scrapingTFG.scrapingTFG.controllers;

import com.scrapingTFG.scrapingTFG.dao.Busqueda.DaoBusqueda;
import com.scrapingTFG.scrapingTFG.dao.Cliente.DaoCliente;
import com.scrapingTFG.scrapingTFG.dao.DataScraping.DaoDataScraping;
import com.scrapingTFG.scrapingTFG.dao.Producto.DaoProducto;
import com.scrapingTFG.scrapingTFG.models.Busqueda;
import com.scrapingTFG.scrapingTFG.models.Cliente;
import com.scrapingTFG.scrapingTFG.models.Datascraping;
import com.scrapingTFG.scrapingTFG.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.chrono.MinguoDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DatascrapingController {

    @Autowired
    private DaoProducto daoProducto;
    @Autowired
    private DaoDataScraping daoDataScraping;


    @RequestMapping(value = "api/scraping/{idProducto}", method = RequestMethod.GET)
    public List<Datascraping> obtenerListadoProductos(@PathVariable Integer idProducto){
        Producto producto = daoProducto.getProducto(idProducto);
        List<Datascraping> scraping = daoDataScraping.getResultados(producto);
        return scraping;
    }
}
