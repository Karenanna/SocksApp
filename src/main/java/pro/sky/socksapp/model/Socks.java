package pro.sky.socksapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Socks {
    @NotBlank
    @NotEmpty
    private Color color;
    private Size size;

    @Positive
    @Max(value = 100)
    @Min(0)

    private int cottonPercent;
    @Positive
    @Min(1)
    private int quantity;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return cottonPercent == socks.cottonPercent && quantity == socks.quantity && color == socks.color && Objects.equals(size, socks.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, size, cottonPercent, quantity);
    }
}
