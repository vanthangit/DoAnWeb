package vn.iotstar.AloTra.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/contact-us")
@Controller
public class ContactUsController {

    @GetMapping("")
    public String contactUs() {
        return "customer/contact-us";
    }
}
