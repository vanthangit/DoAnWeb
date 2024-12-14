package vn.iotstar.AloTra.service.impl;

import java.sql.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.AloTra.entity.Payment;
import vn.iotstar.AloTra.repository.PaymentRepository;
import vn.iotstar.AloTra.service.IPaymentService;

@Service
public class PaymentService implements IPaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public Set<Payment> findPaymentsByTimePeriod(Date startDate, Date endDate, Long branchId) {
		return paymentRepository.findPaymentsByTimePeriod(startDate, endDate, branchId);
	}
	
    public double totalPayment(Date startDate, Date endDate, Long branchId) {
        Set<Payment> payments = findPaymentsByTimePeriod(startDate, endDate, branchId);
        return payments.stream().mapToDouble(Payment::getTotal).sum();
    }


}
