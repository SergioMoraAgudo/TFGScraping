package com.scrapingTFG.scrapingTFG.dao.Producto;

import com.scrapingTFG.scrapingTFG.models.Producto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface DaoProducto {
    Producto getProducto(Integer id);

    Producto getProductoActivo(Integer id);

    void activarProducto(Integer id);

    void desactivarProducto(Integer id);

    void registrarProducto(Producto producto);

    Producto buscarPorNombre(String productoBuscado);
}
