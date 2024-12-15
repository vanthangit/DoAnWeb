package vn.iotstar.AloTra.repository;

import vn.iotstar.AloTra.entity.Voucher;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
	@Query("SELECT v FROM Voucher v WHERE v.end_date < :date")
	Page<Voucher> findExpiredVouchers(@Param("date") LocalDate date, Pageable pageable);
	
	@Query("SELECT v FROM Voucher v WHERE v.end_date > :date")
	Page<Voucher> findValidVouchers(@Param("date") LocalDate date, Pageable pageable);

}
