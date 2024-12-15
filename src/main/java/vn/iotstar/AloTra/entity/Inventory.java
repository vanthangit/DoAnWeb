package vn.iotstar.AloTra.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventory_id;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonManagedReference
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonManagedReference
    private Product product;

    private Integer quantity;
}

