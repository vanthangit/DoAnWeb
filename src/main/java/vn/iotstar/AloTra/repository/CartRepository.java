package vn.iotstar.AloTra.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.iotstar.AloTra.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c.cart_id FROM Cart c WHERE c.user.user_id = :userId")
    Long findCartIdByUserId(@Param("userId") Long userId);
}
