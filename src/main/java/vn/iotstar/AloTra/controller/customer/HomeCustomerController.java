package vn.iotstar.AloTra.controller.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.iotstar.AloTra.dto.ProductDTO;
import vn.iotstar.AloTra.entity.ProductFeedback;
import vn.iotstar.AloTra.service.IProductService;
import vn.iotstar.AloTra.service.impl.OrderLineService;

import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeCustomerController {

    @Autowired
    IProductService productService;

    @Autowired
    OrderLineService orderLineService;

    @GetMapping("")
    public String home(Model model) {
        List<ProductDTO> newProducts = productService.getTop5NewProducts();

        // Tính điểm trung bình rating cho từng sản phẩm
        newProducts.forEach(newProduct -> {
            List<ProductFeedback> relatedFeedbackList = newProduct.getProductFeedbacks();
            double relatedAverageRating = relatedFeedbackList.stream()
                    .mapToDouble(ProductFeedback::getRating)
                    .average()
                    .orElse(0); // Nếu không có feedback, mặc định là 0
            newProduct.setAvg_rating(relatedAverageRating);
        });
        model.addAttribute("newProducts", newProducts);

        // Lấy 5 sản phẩm bán chạy nhất
        List<ProductDTO> featuredProducts = productService.getTop5BestSellingProducts();

        // Tính điểm trung bình rating cho từng sản phẩm bán chạy nhất
        featuredProducts.forEach(topProduct -> {
            List<ProductFeedback> relatedFeedbackList = topProduct.getProductFeedbacks();
            double relatedAverageRating = relatedFeedbackList.stream()
                    .mapToDouble(ProductFeedback::getRating)
                    .average()
                    .orElse(0); // Nếu không có feedback, mặc định là 0
            topProduct.setAvg_rating(relatedAverageRating);
        });

        // Thêm vào model để hiển thị
        model.addAttribute("featuredProducts", featuredProducts);

        return "customer/index";
    }
}
