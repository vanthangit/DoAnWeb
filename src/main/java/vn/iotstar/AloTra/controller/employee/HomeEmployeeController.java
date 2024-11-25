package vn.iotstar.AloTra.controller.employee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class HomeEmployeeController {
    @GetMapping("/home")
    public String home() {
        return "employee/index";
    }
}
