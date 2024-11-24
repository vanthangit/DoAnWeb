package vn.iotstar.AloTra.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="account")
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long account_id;
    @Column(unique = true)
    protected String username;
    protected String password;


    @OneToOne(mappedBy = "account")
    @JsonBackReference
    protected User user;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    protected Role role;

}
