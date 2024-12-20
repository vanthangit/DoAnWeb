package vn.iotstar.AloTra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.dto.CartItemDTO;
import vn.iotstar.AloTra.entity.Cart;
import vn.iotstar.AloTra.entity.CartItem;
import vn.iotstar.AloTra.entity.Product;
import vn.iotstar.AloTra.mapper.CartItemMapper;
import vn.iotstar.AloTra.repository.CartItemRepository;
import vn.iotstar.AloTra.repository.CartRepository;
import vn.iotstar.AloTra.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartItemService {

    CartItemMapper cartItemMapper;
    CartItemRepository cartItemRepository;
    CartRepository cartRepository;
    ProductRepository productRepository;

    public CartItemService(CartItemMapper cartItemMapper, CartItemRepository cartItemRepository, CartRepository cartRepository, ProductRepository productRepository){

        this.cartItemMapper = cartItemMapper;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public CartItemDTO addCartItem(CartItemDTO request, Long user_id){

        // Lay duoc cartId cho User
        Long cartId = cartRepository.findCartIdByUserId(user_id);
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found>>>"));

        Product product = productRepository.findById(request.getProduct_id()).orElseThrow(() -> new RuntimeException("Product not found"));

        //Tim cartitem cho nguoi dung hien tai
        CartItem cartItem = cartItemRepository.findByCart(cart, product);

        if(cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(cartItem);
            return cartItemMapper.toCartItemDto(cartItem);
        }
        else {
            cartItem = cartItemMapper.toCartItem(request);
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItemRepository.save(cartItem);
            return cartItemMapper.toCartItemDto(cartItem);
        }
    }

    public void deleteCartItem(Long cart_item_id){
        cartItemRepository.deleteById(cart_item_id);
    }

    public List<CartItemDTO> getListCartItem (Long user_id){

        //Lấy cartID của user
        Long cart_id = cartRepository.findCartIdByUserId(user_id);
        //Tìm ra cart của user
        Cart cartOfCurrentUser = cartRepository.findById(cart_id).orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cartOfCurrentUser);

        // Map CartItem sang CartItemDTO
        return cartItems.stream()
                .map(cartItem -> cartItemMapper.toCartItemDto(cartItem))
                .collect(Collectors.toList());
    }
}
