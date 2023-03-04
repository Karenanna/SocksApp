package pro.sky.socksapp.services;

import org.springframework.web.client.HttpClientErrorException;
import pro.sky.socksapp.exception.BadRequest;
import pro.sky.socksapp.exception.StockProduct;
import pro.sky.socksapp.model.Color;
import pro.sky.socksapp.model.Socks;
import pro.sky.socksapp.model.SocksSize;

import java.util.Map;

public interface SocksService {


    long addSocks(Socks socks) throws HttpClientErrorException.BadRequest, BadRequest;

    void takeSocksFrom(Socks socks) throws HttpClientErrorException.BadRequest, StockProduct, BadRequest;

    void deleteSocks(Color color, SocksSize socksSize, int cottonPercent, int quantity) throws StockProduct;

    int getSocksQuantity(Color color, SocksSize socksSize, Integer minCottonPercent,
                         Integer maxColorPercent);

    Map<Long, Socks> getSocksMap();
}
