package vn.iotstar.AloTra.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.dto.ProductCreationRequest;
import vn.iotstar.AloTra.dto.ProductDTO;

import java.util.List;

@Service
public interface IProductService {

    //Hàm sửa giá sản phẩm
    ProductDTO editProduct(String product_name, double cost);

    //Hàm tìm kiếm sản phẩm trong admin
    List<ProductDTO> findProductByName(String product_name);

    //Xóa sản phẩm
    void deleteProductByName(String product_name);

    List<ProductDTO> loadAllProducts();

    public List<ProductDTO> getProductsByCategory(String category, String sort);

    public Page<ProductDTO> getAllProducts(int page, int size, String sort);

    public ProductDTO getProductById(Long id);

    public List<ProductDTO> findByCategory(String category, Long excludedProductId);

    public List<ProductDTO> getTop5NewProducts();

    List<ProductDTO> getTop5BestSellingProducts();

    ProductDTO createProduct(ProductCreationRequest request);
}
