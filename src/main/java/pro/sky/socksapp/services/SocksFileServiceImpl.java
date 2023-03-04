package pro.sky.socksapp.services;

import pro.sky.socksapp.exception.BadRequest;
import pro.sky.socksapp.exception.StockProduct;
import pro.sky.socksapp.model.Color;
import pro.sky.socksapp.model.Socks;
import pro.sky.socksapp.model.SocksSize;

import java.util.Map;
import java.util.TreeMap;



public class   SocksFileServiceImpl implements SocksService {

    private final Map<Long, Socks> socksMap = new TreeMap<>();
    private static long id = 1;

    @Override
    public long addSocks(Socks socks) throws BadRequest {
        validate(socks);
        if (socksMap.containsValue(socks)) {
            for (Map.Entry<Long, Socks> entry : socksMap.entrySet()) {
                if (entry.getValue().equals(socks)) {
                    long id = entry.getKey();
                    int oldQuantity = entry.getValue().getQuantity();
                    int newQuantity = oldQuantity + socks.getQuantity();
                    Socks socks1 = new Socks(socks.getColor(), socks.getSize(), socks.getCottonPercent(),
                            newQuantity);
                    socksMap.put(id, socks1);
                    return id;
                }
            }
        } else {
            socksMap.put(id, socks);
        }
        return id++;
    }

    @Override
    public void takeSocksFrom(Socks socks) throws StockProduct, BadRequest {
        validate(socks);
        if (socksMap.containsValue(socks)) {
            for (Map.Entry<Long, Socks> entry : socksMap.entrySet()) {
                if (entry.getValue().equals(socks)) {
                    long id = entry.getKey();
                    int oldQuantity = entry.getValue().getQuantity();
                    int newQuantity = socks.getQuantity();
                    if (oldQuantity >= newQuantity) {
                        int quantity = oldQuantity - newQuantity;
                        Socks socks1 = new Socks(socks.getColor(), socks.getSize(), socks.getCottonPercent(),
                                quantity);
                        socksMap.put(id, socks1);
                    } else {
                        throw new StockProduct("Нет на остатке" + (Math.abs(oldQuantity - newQuantity)));

                    }
                }
            }
        }
    }

    @Override
    public int getSocksQuantity(Color color, SocksSize socksSize, Integer minCottonPercent,
                             Integer maxColorPercent) {
        int count = 0;
        for (Map.Entry<Long, Socks> entry : socksMap.entrySet()) {
            if (color != null && !entry.getValue().getSize().equals(color)) {
                continue;
            }
            if (socksMap != null && !entry.getValue().getSize().equals(socksSize)) {
                continue;
            }
            if (minCottonPercent != null && entry.getValue().getCottonPercent() < minCottonPercent) {
                continue;
            }
            if (maxColorPercent != null && entry.getValue().getCottonPercent() > maxColorPercent) {
                continue;
            }
            count += entry.getValue().getQuantity();
        }
        return count;
    }

    @Override
    public void deleteSocks(Color color, SocksSize socksSize, int cottonPercent, int quantity)
            throws StockProduct {
        Socks socks = new Socks(color, socksSize, cottonPercent, quantity);
        if (socksMap.containsValue(socks)) {
            for (Map.Entry<Long, Socks> entry : socksMap.entrySet()) {
                if (entry.getValue().equals(socks)) {
                    long id = entry.getKey();
                    int oldQuantity = entry.getValue().getQuantity();
                    int defectiveSocks = socks.getQuantity();
                    if (oldQuantity >= defectiveSocks) {
                        int quantityNew = oldQuantity - defectiveSocks;
                        Socks socks1 = new Socks(socks.getColor(), socks.getSize(), socks.getCottonPercent(),
                                quantityNew);
                        socksMap.put(id, socks1);
                    } else {
                        throw new StockProduct("Нечего списать");
                    }
                }
            }
        }
    }
    @Override
    public Map<Long, Socks> getSocksMap() {
        return socksMap;
    }

    private void validate(Socks socks) throws BadRequest {
        if (socks.getColor() == null || socks.getSize() == null) {
            throw new BadRequest("Есть не заполненые поля");
        }
        if (socks.getQuantity() <= 0) {
            throw new BadRequest("quantity долэжен быть больше 0");
        }
        if (socks.getCottonPercent() < 0 || socks.getCottonPercent() > 100) {
            throw new BadRequest("cottonPercent должен быть от 0 до 100");
        }
    }
}
