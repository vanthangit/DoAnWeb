package vn.iotstar.AloTra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long product_id;
    private String product_name;
    private String category;
    private Double cost;
    private String image;
}
