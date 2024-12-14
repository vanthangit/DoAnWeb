package vn.iotstar.AloTra.controller.customer;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.iotstar.AloTra.service.impl.OrderService;
import vn.iotstar.AloTra.service.impl.VNPAYService;

@Controller
public class VNPAYReturn {
    @Autowired
    private VNPAYService vnPayService;
    @Autowired
    private OrderService orderService;
    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);
        Long orderId = Long.parseLong(request.getParameter("vnp_TxnRef"));
        String paymentTime = request.getParameter("vnp_PayDate");
        model.addAttribute("orderId", orderId);
        if (paymentStatus == 1) {
            orderService.updateOrderStatusPaymentTime(orderId, paymentTime);
            return "customer/success-payment";
        } else {
            return "customer/fail-payment";
        }
    }
}
