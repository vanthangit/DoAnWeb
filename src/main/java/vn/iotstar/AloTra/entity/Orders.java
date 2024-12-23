package vn.iotstar.AloTra.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import vn.iotstar.AloTra.enums.OrderStatus;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    private Long order_id;
    private Long user_id;
    private Date order_date;
    private String shipping_address;

    @Enumerated(EnumType.STRING)
    private OrderStatus order_status;

    private Date delivery_date;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<OrderLine> orderLines=new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")
    private Branch branch;
}
