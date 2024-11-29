package vn.iotstar.AloTra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iotstar.AloTra.entity.Cart;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Long product_id;
    private Integer quantity;
    private Long cart_id;
}
