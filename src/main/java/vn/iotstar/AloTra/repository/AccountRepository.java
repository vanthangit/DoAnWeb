package vn.iotstar.AloTra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iotstar.AloTra.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

}
