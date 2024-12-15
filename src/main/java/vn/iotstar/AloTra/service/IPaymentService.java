package vn.iotstar.AloTra.service;

import java.sql.Date;
import java.util.Set;

import org.springframework.stereotype.Service;

import vn.iotstar.AloTra.entity.Payment;

@Service
public interface IPaymentService {
	public Set<Payment> findPaymentsByTimePeriod(Date startDate, Date endDate, Long branchId);

	double totalPayment(Date startDate, Date endDate, Long branchId);
}
