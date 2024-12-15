package vn.iotstar.AloTra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.AloTra.dto.ProductCreationRequest;
import vn.iotstar.AloTra.dto.ProductDTO;
import vn.iotstar.AloTra.entity.Product;
import vn.iotstar.AloTra.mapper.ProductMapper;
import vn.iotstar.AloTra.repository.ProductRepository;
import vn.iotstar.AloTra.service.IProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService implements IProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    //Hàm sửa giá sản phẩm
    @Override
    public ProductDTO editProduct(String product_name, double cost){

        Product editProduct = productRepository.findOneByProductName(product_name);

        editProduct.setCost(cost);
        return productMapper.toDto(productRepository.save(editProduct));
    }

    //Hàm tìm kiếm sản phẩm trong admin
    @Override
    @Transactional
    public List<ProductDTO> findProductByName(String product_name){

        return productRepository.findByProductName(product_name)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    //Xóa sản phẩm
    @Override
    public void deleteProductByName(String product_name){
        Product product = productRepository.findOneByProductName(product_name);
        productRepository.deleteById(product.getProduct_id());
    }

    //Hàm này để load sản phẩm lên admin
    @Override
    public List<ProductDTO> loadAllProducts(){

        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<ProductDTO> getProductsByCategory(String category, String sort) {
        Sort sorting;
        if ("none".equals(sort)) {
            sorting = Sort.unsorted();
        } else {
            Sort.Direction direction = "desc".equalsIgnoreCase(sort) ? Sort.Direction.DESC : Sort.Direction.ASC;
            sorting = Sort.by(direction, "cost");
        }

        List<Product> products = productRepository.findByCategory(category, sorting);
        return products.stream()
                .map(productMapper::toDto) // Chuyển từ Entity sang DTO
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductDTO> getAllProducts(int page, int size, String sort) {
        Pageable pageable;
        if ("none".equals(sort)) {
            pageable = PageRequest.of(page, size);
        } else {
            // Chuyển đổi sort thành Sort.Direction
            Sort.Direction direction = "desc".equalsIgnoreCase(sort) ? Sort.Direction.DESC : Sort.Direction.ASC;
            pageable = PageRequest.of(page, size, Sort.by(direction, "cost"));
        }

        // Lấy danh sách sản phẩm từ repository
        Page<Product> productsPage = productRepository.findAll(pageable);
        return productsPage.map(productMapper::toDto);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto) // Chuyển đổi nếu tồn tại
                .orElse(null); // Trả về null nếu không tìm thấy
    }

    @Override
    public List<ProductDTO> findByCategory(String category, Long excludedProductId) {
        List<Product> products = productRepository.findByCategoryAndIdNot(category, excludedProductId);

        // Chuyển đổi từ Product sang ProductDTO
        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getTop5NewProducts() {
        Pageable topFive = PageRequest.of(0, 5);
        return productRepository.findTop5ByOrderByIdDesc(topFive).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO createProduct(ProductCreationRequest request){

        Product product = productMapper.toProduct(request);
        productRepository.save(product);
        return productMapper.toDto(productRepository.save(product));
    }
}
