package com.scrapingTFG.scrapingTFG.dao.Cliente;

import com.scrapingTFG.scrapingTFG.models.Cliente;

import java.util.List;

public interface DaoCliente {

    Cliente getClienteByEmail(String email);

    void registrarCliente(Cliente cliente);

    Cliente obtenerCredencialesCliente(Cliente cliente);

}
