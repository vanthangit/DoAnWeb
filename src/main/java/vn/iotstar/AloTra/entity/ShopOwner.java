package vn.iotstar.AloTra.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "shop_owner")
public class ShopOwner extends User{
    private String shopName;
}
