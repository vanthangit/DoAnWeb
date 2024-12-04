package vn.iotstar.AloTra.controller.customer;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.iotstar.AloTra.dto.ProductDTO;
import vn.iotstar.AloTra.dto.UserDTO;
import vn.iotstar.AloTra.entity.ProductFeedback;
import vn.iotstar.AloTra.service.IProductService;

import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j
public class HomeCustomerController {

    @Autowired
    IProductService productService;

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
        return "customer/index";
    }
}
