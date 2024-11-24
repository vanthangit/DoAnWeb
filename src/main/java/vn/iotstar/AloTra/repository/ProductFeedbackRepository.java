package vn.iotstar.AloTra.repository;

import vn.iotstar.AloTra.entity.ProductFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFeedbackRepository extends JpaRepository<ProductFeedback, Long> {
}
