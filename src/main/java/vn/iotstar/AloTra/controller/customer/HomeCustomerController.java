package vn.iotstar.AloTra.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeCustomerController {
    @GetMapping("")
    public String home() {
        return "customer/index";
    }
}
