package vn.iotstar.AloTra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreationRequest {

    private String product_name;
    private String category;
    private double cost;
    private String image;
}
