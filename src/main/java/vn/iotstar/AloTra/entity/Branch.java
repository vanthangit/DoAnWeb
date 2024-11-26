package vn.iotstar.AloTra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
