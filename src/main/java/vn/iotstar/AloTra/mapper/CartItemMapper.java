package vn.iotstar.AloTra.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.iotstar.AloTra.dto.CartItemDTO;
import vn.iotstar.AloTra.entity.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItem toCartItem(CartItemDTO cartItemDTO);

    @Mapping(source = "cartItem.cart_item_id", target = "cart_id")
    @Mapping(source = "cartItem.product.product_id", target = "product_id")
    CartItemDTO toCartItemDto(CartItem cartItem);
}
