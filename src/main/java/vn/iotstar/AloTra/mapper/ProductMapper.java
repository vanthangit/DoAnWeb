package vn.iotstar.AloTra.mapper;

import org.mapstruct.Mapper;
import vn.iotstar.AloTra.dto.ProductDTO;
import vn.iotstar.AloTra.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product product);
}
