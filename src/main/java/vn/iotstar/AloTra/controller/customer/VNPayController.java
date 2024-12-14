package vn.iotstar.AloTra.controller.customer;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.AloTra.service.impl.OrderService;
import vn.iotstar.AloTra.service.impl.VNPAYService;


@RestController
public class VNPayController {
    @Autowired
    private VNPAYService vnPayService;

    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @PostMapping("/submitOrder")
    public ResponseEntity<String> submidOrder(@RequestParam("amount") int orderTotal,
                                              @RequestParam("orderId") String orderId,
                                              HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, orderId, baseUrl);
        return ResponseEntity.ok(vnpayUrl);
    }
}
