package vn.iotstar.AloTra.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/about-us")
@Controller
public class AboutUsController {
    @GetMapping("")
    public String aboutUs() {
        return "customer/about-us";
    }
}
