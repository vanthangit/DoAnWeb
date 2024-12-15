package vn.iotstar.AloTra.controller.employee;

import java.sql.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.AloTra.entity.Payment;
import vn.iotstar.AloTra.service.impl.PaymentService;
import vn.iotstar.AloTra.service.impl.UserService;

@Controller
@RequestMapping("/employee")
public class RevenueController {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private UserService userService;

	@GetMapping("/revenue")
	public String getRevenue(@RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, Model model) {

		Long branchId = userService.getMyInfo().getBranch_id();

		Set<Payment> payments = paymentService.findPaymentsByTimePeriod(startDate, endDate, branchId);
		double totalPayment = paymentService.totalPayment(startDate, endDate, branchId);

		model.addAttribute("payments", payments);
		model.addAttribute("totalPayment", totalPayment);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return "employee/revenue";
	}
}
