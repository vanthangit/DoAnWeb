package vn.iotstar.AloTra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.dto.ProductDTO;
import vn.iotstar.AloTra.entity.Product;
import vn.iotstar.AloTra.mapper.ProductMapper;
import vn.iotstar.AloTra.repository.ProductRepository;
import vn.iotstar.AloTra.service.IProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;


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

    public  Product findProductById(Long id) {
        return productRepository.findByProductId(id);
    }

    @Override
    public List<ProductDTO> getTop5BestSellingProducts() {
        Pageable topFive = PageRequest.of(0, 5);
        List<Product> topProducts = productRepository.findTop5BestSellingProducts(topFive);
        return topProducts.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
