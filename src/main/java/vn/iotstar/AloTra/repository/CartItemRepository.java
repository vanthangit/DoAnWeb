package vn.iotstar.AloTra.repository;

import org.springframework.data.jpa.repository.Query;
import vn.iotstar.AloTra.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iotstar.AloTra.entity.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c WHERE c.product = ?1")
    CartItem findByProduct(Product product);

}

