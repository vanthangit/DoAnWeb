package vn.iotstar.AloTra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.iotstar.AloTra.entity.Inventory;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("SELECT i FROM Inventory i WHERE i.product.product_id = :productId AND i.branch.branch_id = :branchId")
    Inventory findByProductIdAndBranchId(@Param("productId") Long productId, @Param("branchId") Long branchId);

    @Query("SELECT i FROM Inventory i WHERE i.branch.branch_id = :branchId")
    List<Inventory> findByBranchBranchId(@Param("branchId") Long branchId);
}

