package vn.iotstar.AloTra.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.AloTra.dto.UserDTO;
import vn.iotstar.AloTra.entity.Product;
import vn.iotstar.AloTra.entity.ProductFeedback;
import vn.iotstar.AloTra.entity.User;
import vn.iotstar.AloTra.service.impl.ProductFeedbackService;
import vn.iotstar.AloTra.service.impl.ProductService;
import vn.iotstar.AloTra.service.impl.UserService;

@Controller
public class FeedbackController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductFeedbackService productFeedbackService;
    @Autowired
    private ProductService productService;


    @GetMapping("/form-feedback")
    public String formFeedback(@RequestParam("productId") Long productId, Model model) {
        ProductFeedback productFeedback = new ProductFeedback();
        model.addAttribute("productFeedback", productFeedback);
        Product product = productService.findProductById(productId);
        model.addAttribute("product",product);
        return "/customer/feedback";
    }

    @PostMapping("/create-feedback")
    public String createFeedback(@ModelAttribute("productFeedback") ProductFeedback productFeedback,
                                 @RequestParam("productId") Long productId,
                                 RedirectAttributes redirectAttributes) {
        UserDTO userDTO = userService.getMyInfo();
        User user = userService.findById(userDTO.getUser_id());
        Product product = productService.findProductById(productId);

        productFeedback.setProduct(product);
        productFeedback.setUser(user);
        productFeedback.setFeedback_date(new java.sql.Date(System.currentTimeMillis()));
        productFeedbackService.createFeedback(productFeedback);

        redirectAttributes.addFlashAttribute("message", "Đánh giá sản phẩm thành công!");
        return "redirect:/form-feedback?productId=" + productId;
    }
}
