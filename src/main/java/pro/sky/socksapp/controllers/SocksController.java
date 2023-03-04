package pro.sky.socksapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import pro.sky.socksapp.exception.BadRequest;
import pro.sky.socksapp.exception.StockProduct;
import pro.sky.socksapp.model.Color;
import pro.sky.socksapp.model.Socks;
import pro.sky.socksapp.model.SocksSize;
import pro.sky.socksapp.services.SocksService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/socks")
@RequiredArgsConstructor
@Tag(name = "Склад носков", description = "CRUD-операции для работы с поставками носков")
public class SocksController {
    private final SocksService socksService;

    @PostMapping
    @Operation (summary = "Добавление носков на склад")
    public ResponseEntity<Long> addSocks (@Valid @RequestBody Socks socks)
            throws HttpClientErrorException.BadRequest, BadRequest {
        long id = socksService.addSocks(socks);
       return ResponseEntity.ok(id);
    }


    @PutMapping
    @Operation(summary =  "Отгрузка со склада")
    public ResponseEntity<Void> takeSocks (@Valid @RequestBody Socks socks)
        throws StockProduct, BadRequest {
        socksService.takeSocksFrom(socks);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Остаток носков на складе")
    public ResponseEntity<Integer> howMeanySocks(@RequestParam(name = "color") Color color,
                                                 @RequestParam(name = "size") SocksSize socksSize,
                                                 @RequestParam(name = "minCottonPercent") Integer minCottonPercent,
                                                 @RequestParam(name = "maxCottonPercent") Integer maxCottonPercent) {
        Integer quantity = SocksService.getSocksQuantity(color,socksSize,minCottonPercent,maxCottonPercent);
        return ResponseEntity.ok().body(quantity);
    }

    @DeleteMapping
    @Operation (summary = "Списание бракованных носков")
    public ResponseEntity<Void> deletedSocks(@RequestParam(name = "color") Color color,
                                             @RequestParam (name = "size") SocksSize socksSize,
                                             @RequestParam (name = "cottonPercent" ) Integer cottonPercent,
                                             @RequestParam (name = "quantity") Integer quantity) throws StockProduct {

        socksService.deleteSocks(color,socksSize, cottonPercent, quantity);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/get/all")
    @Operation(summary = "Получение всех видов носков", description = "Входные данные не нужны")
    public ResponseEntity<Map<Long, Socks>> getAllSocks() {
        Map<Long, Socks> socksMap = socksService.getSocksMap();
        if (socksMap != null) {
            return ResponseEntity.ok(socksMap);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<String> handleValidationExceptions(BadRequest e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(StockProduct.class)
    public ResponseEntity<String> handleValidationExceptions(StockProduct e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
