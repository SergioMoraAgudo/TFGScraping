package com.scrapingTFG.scrapingTFG.dao.Busqueda;

import com.scrapingTFG.scrapingTFG.models.Busqueda;
import com.scrapingTFG.scrapingTFG.models.Cliente;
import com.scrapingTFG.scrapingTFG.models.Producto;

import java.util.List;

public interface DaoBusqueda {

    List<Busqueda> obtenerBusquedaDeCliente(Cliente cliente);

    List<Busqueda> obtenerBusquedaDeProducto(Producto producto);

    Busqueda obtenerBusquedaEspecifica(Cliente cliente,Producto producto);

    void desactivarBusqueda(Cliente cliente, Producto producto);


    void registrarBusqueda(Busqueda busqueda);
}
