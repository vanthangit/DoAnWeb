package vn.iotstar.AloTra.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branch_id;
    private String branch_name;
    private String branch_address;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @JsonBackReference
    private Orders orders;
}
