package vn.iotstar.AloTra.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.iotstar.AloTra.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category, Sort sort);

    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.product_id != :excludedProductId ORDER BY p.product_id DESC")
    List<Product> findByCategoryAndIdNot(@Param("category") String category, @Param("excludedProductId") Long excludedProductId);

    @Query("SELECT p FROM Product p ORDER BY p.product_id ASC")
    List<Product> findTop5ByOrderByIdDesc(Pageable pageable);
}
