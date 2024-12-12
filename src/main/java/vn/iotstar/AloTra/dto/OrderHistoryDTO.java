package vn.iotstar.AloTra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iotstar.AloTra.entity.Branch;
import vn.iotstar.AloTra.enums.OrderStatus;

import java.sql.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderHistoryDTO {
    private Long order_id;
    private Date order_date;
    private String shipping_address;
    private OrderStatus order_status;
    private Branch branch;
    private Set<ProductHistoryDTO> products;
    private Double totalCost;
}
