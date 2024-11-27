package vn.iotstar.AloTra.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.dto.ProductDTO;

import java.util.List;

@Service
public interface IProductService {
    public List<ProductDTO> getProductsByCategory(String category);

    public Page<ProductDTO> getAllProducts(int page, int size, String sort);
}
