package vn.iotstar.AloTra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.iotstar.AloTra.entity.Orders;
import vn.iotstar.AloTra.entity.Payment;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE p.payment_method like ?1")
    Payment findByPaymentMethod(String paymentMethod);

    Optional<Payment> findByOrder(Orders order);
}
