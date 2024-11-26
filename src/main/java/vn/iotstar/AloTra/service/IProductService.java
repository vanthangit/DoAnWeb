package vn.iotstar.AloTra.service;

import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.dto.ProductDTO;

import java.util.List;

@Service
public interface IProductService {
    public List<ProductDTO> getProductsByCategory(String category);

    public List<ProductDTO> getAllProducts();
}
