package vn.iotstar.AloTra.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.OneToMany;
import lombok.*;
import vn.iotstar.AloTra.entity.CartItem;
import vn.iotstar.AloTra.entity.OrderLine;
import vn.iotstar.AloTra.entity.ProductFeedback;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long product_id;
    private String product_name;
    private String category;
    private Double cost;
    private String image;
    private List<CartItem> cartItems;
    private List<OrderLine> orderLines;
    private List<ProductFeedback> productFeedbacks;
}
