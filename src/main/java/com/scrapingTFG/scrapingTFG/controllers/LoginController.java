package com.scrapingTFG.scrapingTFG.controllers;

import com.scrapingTFG.scrapingTFG.dao.Cliente.DaoCliente;
import com.scrapingTFG.scrapingTFG.models.Cliente;
import com.scrapingTFG.scrapingTFG.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private DaoCliente daoCliente;
    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping(value="api/login",method= RequestMethod.POST)
    public Map loginCliente(@RequestBody Cliente cliente){
        Map respuesta=new HashMap();
        Cliente clienteLogeado= daoCliente.obtenerCredencialesCliente(cliente);

        if(clienteLogeado!=null)
        {
            // Si la contraseña es correcta, creo el token y se lo envio al front
            String token=jwtUtils.create(String.valueOf(clienteLogeado.getId()),clienteLogeado.getEmail());
            respuesta.put("token",token);
            respuesta.put("success","OK");
        }
        else respuesta.put("success","FAIL");

        return respuesta;
    }

}
