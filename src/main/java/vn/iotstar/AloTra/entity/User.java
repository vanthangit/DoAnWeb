package vn.iotstar.AloTra.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import vn.iotstar.AloTra.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
public abstract class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long user_id;
    protected String full_name;
    protected String phone;
    @Enumerated(EnumType.STRING)
    protected Gender gender;
    protected String address;

    @Column(unique = true)
    protected String email;

    protected String password;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;
}
