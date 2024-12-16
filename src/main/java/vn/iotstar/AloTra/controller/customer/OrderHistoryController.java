package vn.iotstar.AloTra.controller.customer;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.AloTra.dto.OrderHistoryDTO;
import vn.iotstar.AloTra.dto.UserDTO;
import vn.iotstar.AloTra.enums.OrderStatus;
import vn.iotstar.AloTra.service.impl.OrderService;
import vn.iotstar.AloTra.service.impl.UserService;

import java.util.Set;

@Controller
@RequestMapping("/customer/order-history")
public class OrderHistoryController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderHistoryController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("")
    public String getOrderHistory(
            @RequestParam(value = "tab", required = false, defaultValue = "tat-ca-don-hang") String tab,
            Model model, HttpSession session) {

        UserDTO currentUser = userService.getMyInfo();
        if (currentUser == null) {
            return "redirect:auth/form-login"; // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
        }

        Long customerId = currentUser.getUser_id();
        Set<OrderHistoryDTO> orders;

        // Ánh xạ trực tiếp từ "tab" sang trạng thái đơn hàng
        switch (tab) {
            case "tat-ca-don-hang":
                orders = orderService.getAllOrders(customerId); // Lấy tất cả đơn hàng của người dùng
                break;
            case "don-cho-xac-nhan":
                orders = orderService.getPurchaseHistory(customerId, OrderStatus.PENDING); // Đơn chờ xác nhận
                break;
            case "don-da-xac-nhan":
                orders = orderService.getPurchaseHistory(customerId, OrderStatus.CONFIRMED); // Đơn đã xác nhận
                break;
            case "don-dang-van-chuyen":
                orders = orderService.getPurchaseHistory(customerId, OrderStatus.SHIPPING); // Đơn đang vận chuyển
                break;
            case "don-da-giao":
                orders = orderService.getPurchaseHistory(customerId, OrderStatus.COMPLETED); // Đơn đã giao
                break;
            case "don-huy":
                orders = orderService.getPurchaseHistory(customerId, OrderStatus.CANCELLED);
                break;
            default:
                orders = orderService.getAllOrders(customerId); // Mặc định là tất cả đơn hàng của người dùng
                break;
        }

        // Gửi thông tin đến view
        model.addAttribute("orders", orders);
        model.addAttribute("tab", tab);
        return "customer/order-history";
    }

    @GetMapping("/cancel")
    public String cancelOrder(@RequestParam("orderId") Long orderId, RedirectAttributes redirectAttributes) {
        UserDTO currentUser = userService.getMyInfo();
        if (currentUser == null) {
            return "redirect:/auth/form-login";
        }

        orderService.cancelOrder(orderId);
        redirectAttributes.addFlashAttribute("message", "Hủy đơn hàng thành công.");

        return "redirect:/customer/order-history";
    }

}
