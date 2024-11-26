package vn.iotstar.AloTra.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDto) // Map từ Entity sang DTO
                .collect(Collectors.toList());
    }
}
