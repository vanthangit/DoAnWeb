package vn.iotstar.AloTra.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import vn.iotstar.AloTra.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String full_name;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;

    @Column(unique = true)
    private String email;

    private String password;


    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
