package com.scrapingTFG.scrapingTFG.dao.Cliente;


import com.scrapingTFG.scrapingTFG.models.Cliente;
import com.scrapingTFG.scrapingTFG.models.Producto;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class DaoClienteImp implements DaoCliente {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Cliente getClienteByEmail(String email) {
                System.out.println(email);
        String query="FROM Cliente c WHERE c.email=:email";
        List<Cliente> lista=entityManager.createQuery(query,Cliente.class)
                .setParameter("email",email)
                .getResultList();
        if (lista.isEmpty()) return null;
        return lista.get(0);
    }

    @Override
    public void registrarCliente(Cliente cliente) {
        entityManager.merge(cliente);
    }

    @Override
    public Cliente obtenerCredencialesCliente(Cliente cliente) {
        String query="FROM Cliente where email=:email";
        List<Cliente> lista=entityManager.createQuery(query,Cliente.class)
                .setParameter("email",cliente.getEmail())
                .getResultList();
        if (lista.isEmpty()) return null;
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(lista.get(0).getPass(),cliente.getPass().getBytes()))
            return lista.get(0);
        else return null;
    }

}
