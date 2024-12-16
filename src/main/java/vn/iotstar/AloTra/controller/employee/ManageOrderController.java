package vn.iotstar.AloTra.controller.employee;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import vn.iotstar.AloTra.entity.Orders;
import vn.iotstar.AloTra.enums.OrderStatus;
import vn.iotstar.AloTra.service.impl.OrderService;
import vn.iotstar.AloTra.service.impl.UserService;



@Controller
@RequestMapping("/employee/order-management")
public class ManageOrderController {
	@Autowired
    private OrderService orderService;
	
	@Autowired
    private UserService userService;


    @GetMapping("")
    public String getOrderPage(
            @RequestParam(value = "tab", required = false, defaultValue = "tat-ca-don-hang") String tab,
            Model model) {

        Long branchId = userService.getMyInfo().getBranch_id();
        Set<Orders> orders;

        switch (tab) {
            case "tat-ca-don-hang":
                orders = orderService.getAllOrdersByBranch(branchId); 
                break;
            case "don-cho-xac-nhan":
                orders = orderService.getOrdersByStatusAndBranch(OrderStatus.PENDING, branchId); 
                break;
            case "don-da-xac-nhan":
                orders = orderService.getOrdersByStatusAndBranch(OrderStatus.CONFIRMED, branchId); 
                break;
            case "don-dang-van-chuyen":
                orders = orderService.getOrdersByStatusAndBranch(OrderStatus.SHIPPING, branchId); 
                break;
            case "don-da-giao":
                orders = orderService.getOrdersByStatusAndBranch(OrderStatus.COMPLETED, branchId); 
                break;
            case "don-huy":
                orders = orderService.getOrdersByStatusAndBranch(OrderStatus.CANCELLED, branchId); 
                break;
            default:
                orders = orderService.getAllOrdersByBranch(branchId); 
                break;
        }

        model.addAttribute("Orders", orders);
        model.addAttribute("tab", tab);

        return "employee/order-management";
    }

    @PostMapping
    public String updateOrderStatus(@RequestParam Long orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/employee/order-management";
    }
    

    @GetMapping("/search")
    public String searchOrdersByDate(@RequestParam("orderDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, Model model) {
        Long branchId = userService.getMyInfo().getBranch_id();
        Set<Orders> orders = orderService.findOrdersByDate(java.sql.Date.valueOf(date), branchId);


        model.addAttribute("searchOrders", orders); 
        model.addAttribute("orderDate", date);

        return "employee/order-management";
    }
}
