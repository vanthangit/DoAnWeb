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
@Table(name = "[user]")
public abstract class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long userId;
    protected String fullName;
    protected String phone;
    @Enumerated(EnumType.STRING)
    protected Gender gender;
    protected String address;
    protected String email;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "accountId")
    @JsonManagedReference
    protected Account account;

}
