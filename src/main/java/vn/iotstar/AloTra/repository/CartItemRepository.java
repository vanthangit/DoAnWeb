package vn.iotstar.AloTra.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.iotstar.AloTra.entity.Cart;
import vn.iotstar.AloTra.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iotstar.AloTra.entity.Product;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c WHERE c.product = ?1")
    CartItem findByProduct(Product product);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart = ?1")
    List<CartItem> findAllByCartId(Cart cart);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.cart = ?1")
    void deleteByCart(Cart cart);


    @Query("SELECT ci FROM CartItem ci WHERE " +
            "ci.cart = ?1 AND ci.product = ?2")
    CartItem findByCart(Cart cart, Product product);

}

