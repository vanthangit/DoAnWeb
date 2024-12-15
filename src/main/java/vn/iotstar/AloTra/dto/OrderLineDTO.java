package vn.iotstar.AloTra.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineDTO {

    private Long product_id;

    private Integer quantity;

    private Long orders_id;
}
