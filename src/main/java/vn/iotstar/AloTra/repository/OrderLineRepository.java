package vn.iotstar.AloTra.repository;

import vn.iotstar.AloTra.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

}
