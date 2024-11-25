package vn.iotstar.AloTra.controller.owner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owner")
public class HomeOwnerController {
    @GetMapping("/home")
    public String home() {
        return "owner/index";
    }
}
