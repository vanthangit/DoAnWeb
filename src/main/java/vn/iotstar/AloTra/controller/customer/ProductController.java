package vn.iotstar.AloTra.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.iotstar.AloTra.dto.ProductDTO;
import vn.iotstar.AloTra.service.IProductService;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/{category}")
    public String getProductsByCategory(@PathVariable String category, Model model) {
        List<ProductDTO> products = productService.getProductsByCategory(category);
        model.addAttribute("category", category);  // Gửi category vào model
        model.addAttribute("products", products);  // Gửi sản phẩm theo category
        return "customer/products";
    }

    @GetMapping("")
    public String getAllProducts(Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);  // Gửi sản phẩm theo category
        return "customer/products";
    }
}
