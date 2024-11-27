package vn.iotstar.AloTra.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.iotstar.AloTra.dto.ProductDTO;
import vn.iotstar.AloTra.service.IProductService;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    IProductService productService;

    @GetMapping("")
    public String getAllProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(required = false) String sort,
                                 Model model) {
        if (sort == null) {
            sort = "none";
        }
        Page<ProductDTO> productPage = productService.getAllProducts(page, size, sort);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("hasPagination", productPage.getTotalPages() > 1);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("sort", sort);

        return "customer/products";
    }

    @GetMapping("/{category}")
    public String getProductsByCategory(@PathVariable String category, Model model) {
        List<ProductDTO> products = productService.getProductsByCategory(category);
        model.addAttribute("category", category);  // Gửi category vào model
        model.addAttribute("products", products);  // Gửi sản phẩm theo category
        model.addAttribute("hasPagination", false);
        return "customer/products";
    }
}
