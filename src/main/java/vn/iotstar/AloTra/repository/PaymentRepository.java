package vn.iotstar.AloTra.repository;

import java.sql.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.iotstar.AloTra.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	@Query("SELECT p FROM Payment p "
		       + "JOIN p.order o "
		       + "WHERE (:startDate IS NULL OR p.payment_date >= :startDate) "
		       + "AND (:endDate IS NULL OR p.payment_date <= :endDate) "
		       + "AND p.payment_status = 'PAID' "
		       + "AND o.order_status <> 'CANCELLED' "
		       + "AND o.branch.branch_id = :branchId")
		Set<Payment> findPaymentsByTimePeriod(@Param("startDate") Date startDate,
		                                      @Param("endDate") Date endDate,
		                                      @Param("branchId") Long branchId);


}
