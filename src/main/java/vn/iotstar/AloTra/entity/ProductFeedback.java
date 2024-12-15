package vn.iotstar.AloTra.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "product_feedback")
public class ProductFeedback{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_feedback_id;
    @Column(columnDefinition = "LONGTEXT")
    private String comment;
    private Date feedback_date;

    private Double rating;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;
}
