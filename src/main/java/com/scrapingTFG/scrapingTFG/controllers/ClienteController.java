package com.scrapingTFG.scrapingTFG.controllers;

import com.scrapingTFG.scrapingTFG.dao.Cliente.DaoCliente;
import com.scrapingTFG.scrapingTFG.models.Cliente;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    @Autowired
    private DaoCliente daoCliente;

    @RequestMapping(value = "api/cliente", method = RequestMethod.POST)
    public void registrarCliente(@RequestBody Cliente cliente) {

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, cliente.getPass()); //Haseo de contraseña
        cliente.setPass(hash);
        daoCliente.registrarCliente(cliente);
    }
}
