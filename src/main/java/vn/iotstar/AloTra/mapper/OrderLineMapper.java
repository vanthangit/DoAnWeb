package vn.iotstar.AloTra.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.iotstar.AloTra.entity.CartItem;
import vn.iotstar.AloTra.entity.OrderLine;

@Mapper(componentModel = "spring")
public interface OrderLineMapper {

    // Map CartItem sang OrderLine
    @Mapping(source = "product", target = "product") // Map Product
    @Mapping(source = "quantity", target = "quantity") // Map Quantity
    OrderLine cartItemToOrderLine(CartItem cartItem);


}
