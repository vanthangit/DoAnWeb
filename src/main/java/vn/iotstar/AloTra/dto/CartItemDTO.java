package vn.iotstar.AloTra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Long cart_item_id;
    private Long product_id;
    private Integer quantity;
    private Long cart_id;
}
