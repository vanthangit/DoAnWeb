package vn.iotstar.AloTra.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.AloTra.dto.CartItemDTO;
import vn.iotstar.AloTra.service.impl.CartItemService;

@RestController
@RequestMapping("/api/cartitems")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartItemDTO> addCartItem(@RequestBody CartItemDTO request){

        CartItemDTO saveCartItem = cartItemService.addCartItem(request);

        return new ResponseEntity<>(saveCartItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/{cart_item_id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long cart_item_id){

        cartItemService.deleteCartItem(cart_item_id);
        return new ResponseEntity<String>("CartItem have been deleted", HttpStatus.OK);
    }
}
