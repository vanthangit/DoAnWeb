package vn.iotstar.AloTra.controller.customer;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user-info")
@Controller
public class UserInformationController {
    @GetMapping
    public String userInformation() {
        return "customer/user-information";
    }
}
