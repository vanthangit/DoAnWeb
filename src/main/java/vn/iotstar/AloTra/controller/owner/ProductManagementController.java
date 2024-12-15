package vn.iotstar.AloTra.controller.owner;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductManagementController {

    @GetMapping("/product-managements")
    public String home() {
        return "/owner/product-management";
    }
}
