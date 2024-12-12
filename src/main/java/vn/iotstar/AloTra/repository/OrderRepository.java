package vn.iotstar.AloTra.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.iotstar.AloTra.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iotstar.AloTra.enums.OrderStatus;

import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query("SELECT o FROM Orders o WHERE o.user_id = :userId")
    Set<Orders> findOrdersByUserId(@Param("userId") Long userId);

    @Query("SELECT o FROM Orders o WHERE o.user_id = :userId AND o.order_status = :orderStatus")
    Set<Orders> findOrdersByUserIdAndOrderStatus(@Param("userId") Long userId, @Param("orderStatus") OrderStatus orderStatus);
}
