package vn.iotstar.AloTra.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.AloTra.dto.ProductCreationRequest;
import vn.iotstar.AloTra.dto.ProductDTO;
import vn.iotstar.AloTra.entity.ProductFeedback;
import vn.iotstar.AloTra.service.IProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    IProductService productService;

    @GetMapping("")
    public String getAllProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(required = false) String sort,
                                 @RequestParam(required = false) String category,
                                 Model model) {
        if (sort == null) {
            sort = "none";
        }

        List<ProductDTO> products;
        boolean hasPagination = true;

        if (category != null && !category.isEmpty()) {
            // Lấy sản phẩm theo category
            products = productService.getProductsByCategory(category, sort);
            hasPagination = false;
        } else {
            // Lấy toàn bộ sản phẩm có phân trang
            Page<ProductDTO> productPage = productService.getAllProducts(page, size, sort);
            products = productPage.getContent();
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", productPage.getTotalPages());
            model.addAttribute("totalItems", productPage.getTotalElements());
            hasPagination = productPage.getTotalPages() > 1;
        }

        // Tính điểm trung bình rating cho từng sản phẩm
        products.forEach(product -> {
            List<ProductFeedback> feedbackList = product.getProductFeedbacks();
            double averageRating = feedbackList.stream()
                    .mapToDouble(ProductFeedback::getRating)
                    .average()
                    .orElse(0); // Nếu không có feedback, mặc định là 0
            product.setAvg_rating(averageRating);
        });

        model.addAttribute("products", products);
        model.addAttribute("category", category);
        model.addAttribute("hasPagination", hasPagination);
        model.addAttribute("sort", sort);

        return "customer/products";
    }


    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        // Lấy thông tin sản phẩm từ service
        ProductDTO product = productService.getProductById(id);
        // Tính toán trung bình rating
        List<ProductFeedback> feedbackList = product.getProductFeedbacks();
        double averageRating = feedbackList.stream()
                .mapToDouble(ProductFeedback::getRating)
                .average()
                .orElse(0); // Nếu không có feedback, mặc định là 0
        product.setAvg_rating(averageRating);

        //Lấy sản phẩm liên quan
        List<ProductDTO> relatedProducts = productService.findByCategory(product.getCategory(), id);

        // Tính điểm trung bình rating cho từng sản phẩm
        relatedProducts.forEach(relatedProduct -> {
            List<ProductFeedback> relatedFeedbackList = relatedProduct.getProductFeedbacks();
            double relatedAverageRating = relatedFeedbackList.stream()
                    .mapToDouble(ProductFeedback::getRating)
                    .average()
                    .orElse(0); // Nếu không có feedback, mặc định là 0
            relatedProduct.setAvg_rating(relatedAverageRating);
        });


        // Thêm sản phẩm vào model
        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);

        // Trả về tên view hiển thị sản phẩm chi tiết
        return "customer/product-detail";
    }


    //TIM KIEM ADMIN
    @GetMapping("/filter")
    public ResponseEntity<List<ProductDTO>> findProductByName(@RequestParam(value = "product_name", required = false) String product_name) {

        List<ProductDTO> productDTOList = productService.findProductByName(product_name);
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    //UPDATE SAN PHAM
    @PutMapping("/{product_name}/{cost}")
    public ResponseEntity<ProductDTO> editProduct(@PathVariable String product_name, @PathVariable double cost) {

        ProductDTO productDTO = productService.editProduct(product_name, cost);

        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    //XOA SAN PHAM ADMIN
    @DeleteMapping("/{product_name}")
    public ResponseEntity<String> deleteProductByName(@PathVariable String product_name) {

        productService.deleteProductByName(product_name);
        return new ResponseEntity<>("Product have been deleted!", HttpStatus.OK);
    }

    //ADMIN
    @GetMapping("/loadAllProducts")
    public ResponseEntity<List<ProductDTO>> loadAllProducts() {

        return new ResponseEntity<>(productService.loadAllProducts(), HttpStatus.OK);
    }

    //Hàm tạo mới sản phẩm
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductCreationRequest request) {

        return new ResponseEntity<>(productService.createProduct(request), HttpStatus.OK);
    }
}
