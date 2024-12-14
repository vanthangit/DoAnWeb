package vn.iotstar.AloTra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long order_id;
    private String branch_name;
    private String shipping_address;
    private String payment_method;
    private Double total_amount;
}
