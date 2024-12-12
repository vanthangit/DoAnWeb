package vn.iotstar.AloTra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductHistoryDTO {
    private Long product_id;
    private String product_name;
    private Integer quantity;
    private String image;
    private Double cost;
    private String category;
}
