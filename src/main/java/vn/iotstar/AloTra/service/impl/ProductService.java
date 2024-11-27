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
    public List<ProductDTO> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        return products.stream()
                .map(productMapper::toDto) // Map từ Entity sang DTO
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

}
