package com.scrapingTFG.scrapingTFG.dao.DataScraping;

import com.scrapingTFG.scrapingTFG.models.Datascraping;
import com.scrapingTFG.scrapingTFG.models.Producto;

import java.util.List;

public interface DaoDataScraping {
    List<Datascraping> getResultados(Producto producto);

}
