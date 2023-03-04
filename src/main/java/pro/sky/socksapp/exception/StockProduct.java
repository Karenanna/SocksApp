package pro.sky.socksapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus (HttpStatus.BAD_REQUEST)
public class StockProduct extends Exception{
    public StockProduct(String message) {
        super(message);
    }


}
