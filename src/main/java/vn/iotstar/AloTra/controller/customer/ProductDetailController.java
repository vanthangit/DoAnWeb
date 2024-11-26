package vn.iotstar.AloTra.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductDetailController {
    @GetMapping("")
    public String productDetail() {
        return "customer/product-detail";
    }
}
