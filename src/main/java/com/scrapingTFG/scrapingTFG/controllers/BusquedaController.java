package com.scrapingTFG.scrapingTFG.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrapingTFG.scrapingTFG.dao.Busqueda.DaoBusqueda;
import com.scrapingTFG.scrapingTFG.dao.Cliente.DaoCliente;
import com.scrapingTFG.scrapingTFG.dao.Producto.DaoProducto;
import com.scrapingTFG.scrapingTFG.models.Busqueda;
import com.scrapingTFG.scrapingTFG.models.BusquedaId;
import com.scrapingTFG.scrapingTFG.models.Cliente;
import com.scrapingTFG.scrapingTFG.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.scrapingTFG.scrapingTFG.utils.JWTUtils;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BusquedaController {

    @Autowired
    private DaoCliente daoCliente;
    @Autowired
    private DaoBusqueda daoBusqueda;
    @Autowired
    private DaoProducto daoProducto;
    @Autowired
    private JWTUtils jwtUtil;


    //se le pasa al front una lista de busquedas activas que tiene el cliente
    @RequestMapping(value = "api/busquedas/{email}", method = RequestMethod.GET)
    public List<Busqueda> obtenerBusquedaDelCliente(@RequestHeader(value="Authorization") String token,
                                                    @PathVariable String email){
        Cliente clienteSolicitante = daoCliente.getClienteByEmail(email);
        List<Busqueda> busqueda = daoBusqueda.obtenerBusquedaDeCliente(clienteSolicitante);
        if (!validarToken(token)) {
            return null;
        }
        return busqueda;
    }
/*
    @RequestMapping(value = "api/busquedas/{email}", method = RequestMethod.GET)
    public List<Producto> obtenerProductoDeBusquedaDelCliente(@PathVariable String email){
        //System.out.println("------------------>>>>> "+cliente.getEmail());
        Cliente clienteSolicitante = daoCliente.getClienteByEmail(email);
        //System.out.println("------------------>>>>> "+clienteSolicitante.getId()+"   ---  "+clienteSolicitante.getEmail());
        List<Busqueda> busquedas = daoBusqueda.obtenerBusquedaDeCliente(clienteSolicitante);
        //System.out.println("------------------>>>>> "+busquedas.get(0).getId().getIdproducto());
        List<Producto> productosBuscados = new ArrayList<>();
        for (Busqueda b:busquedas) {
            System.out.println("------------------>>>>> FOREACH   "+daoProducto.getProducto(b.getIdproducto().getId()).getNombre());
            productosBuscados.add(daoProducto.getProducto(b.getIdproducto().getId()));
        }
        System.out.println("----------------->>>>> "+productosBuscados.get(0).getId());
        System.out.println("LISTA CREADA PARA ENVIAR");
        return productosBuscados;
    }*/

    //Envia al front una lista de productos que ha buscado el cliente solicitante
    @RequestMapping(value = "api/busquedas/{idProducto}", method = RequestMethod.POST)
    public List<Producto> obtenerProductoDeBusquedaDelCliente(@RequestHeader(value="Authorization") String token,
                                                              @PathVariable Integer idProducto,
                                                             @RequestBody Cliente cliente){
        Cliente clienteSolicitante = daoCliente.getClienteByEmail(cliente.getEmail());
        List<Busqueda> busquedas = daoBusqueda.obtenerBusquedaDeCliente(clienteSolicitante);
        List<Producto> productosBuscados = new ArrayList<>();
        for (Busqueda b:busquedas) {
            productosBuscados.add(daoProducto.getProducto(b.getIdproducto().getId()));
        }
        if (!validarToken(token)) return null;
        return productosBuscados;
    }

    /*@RequestMapping(value = "api/busquedas/", method = RequestMethod.POST)
    public String jsonprueba(@RequestHeader(value="Authorization") String token,
                             @RequestBody Cliente cliente) throws JsonProcessingException {
        List<Producto> lp= obtenerProductoDeBusquedaDelCliente(cliente);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        for (Producto p:lp) {
            jsonString = mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(p);
        }
        return jsonString;
    }
*/

    //se da de baja una busqueeda de un cliente
    @RequestMapping(value = "api/busqueda/{email}", method = RequestMethod.POST)
    public void cancelarBusqueda(@PathVariable String email, @RequestBody Integer idproducto){
        Cliente cliente = daoCliente.getClienteByEmail(email);
        Producto producto=daoProducto.getProducto(idproducto);
        daoBusqueda.desactivarBusqueda(cliente,producto);
    }


/*
    @RequestMapping(value = "api/busqueda", method = RequestMethod.POST)
    public void registrarBusqueda(@RequestBody String email, @RequestBody String productoBuscado){
        System.out.println(email+"  ---------------------  "+ productoBuscado);
        Cliente cliente = daoCliente.getClienteByEmail(email);
        Producto producto = daoProducto.buscarPorNombre(productoBuscado);
        System.out.println(cliente+"  ---------------------  "+ producto);
        if (producto==null){//el producto no se encuentra en la base de datos
            producto.setNombre(productoBuscado);
            daoProducto.registrarProducto(producto);
        }
        producto.setActivo(1);// activamos el producto (aun que ya esté activo no importa)
        Busqueda busqueda = daoBusqueda.obtenerBusquedaEspecifica(cliente, producto);

        if (busqueda==null){
            busqueda.setIdcliente(cliente);
            busqueda.setIdproducto(producto);
        }
        busqueda.setActivo(1);

        daoBusqueda.registrarBusqueda(busqueda);

    }

// el de abajo es una copia por metodo get del anterior
*/
    //Registra una nueva busqueda que realice el cliente
    @RequestMapping(value = "api/busqueda/{email}/{productoBuscado}", method = RequestMethod.GET)
    public void registraBusqueda(@PathVariable String email, @PathVariable String productoBuscado){
        Cliente cliente = daoCliente.getClienteByEmail(email);
        Producto producto = daoProducto.buscarPorNombre(productoBuscado);
        Busqueda busqueda = null;
        if (producto==null){//el producto no se encuentra en la base de datos
            Producto nuevoProducto = new Producto();
            nuevoProducto.setNombre(productoBuscado);
            nuevoProducto.setActivo(1);
            daoProducto.registrarProducto(nuevoProducto);
            producto = nuevoProducto;
        }
        else{
            producto.setActivo(1);// activamos el producto (aun que ya esté activo no importa)
            busqueda = daoBusqueda.obtenerBusquedaEspecifica(cliente, producto);
        }

        if (busqueda==null){
            Busqueda nuevaBusqueda = new Busqueda();
            nuevaBusqueda.setIdcliente(cliente);
            nuevaBusqueda.setIdproducto(producto);
            nuevaBusqueda.setActivo(1);
            busqueda = nuevaBusqueda;
        }
        else busqueda.setActivo(1);

        BusquedaId bId = new BusquedaId();
        bId.setIdcliente(cliente.getId());
        bId.setIdproducto(producto.getId());
        busqueda.setId(bId);
        daoBusqueda.registrarBusqueda(busqueda);

    }





    // función de apoyo
    private boolean validarToken(String token){
        String usuarioid=jwtUtil.getKey(token);
        System.out.println(usuarioid+"  ---------------------  "+ token);
        return usuarioid!=null;
    }
}
