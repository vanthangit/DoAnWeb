package vn.iotstar.AloTra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "voucher")
public class Voucher{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voucher_id;
    private Double voucher_value;
    @Column(name = "start_date")  // Đảm bảo ánh xạ đúng cột trong DB
    private LocalDate start_date;

    @Column(name = "end_date")    // Ánh xạ tên cột "end_date" từ DB
    private LocalDate end_date;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Orders order;
}