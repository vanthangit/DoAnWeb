package vn.iotstar.AloTra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.iotstar.AloTra.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    @Query("SELECT b FROM Branch b WHERE b.branch_name = :branchName")
    Branch findByBranchName(@Param("branchName") String branchName);
    
    @Query("SELECT b FROM Branch b WHERE b.branch_id = :branchId")
    Branch findByBranchId(@Param("branchId") Long branchId);
}