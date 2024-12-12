package vn.iotstar.AloTra.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.AloTra.dto.CartItemDTO;
import vn.iotstar.AloTra.dto.UserDTO;
import vn.iotstar.AloTra.service.impl.CartItemService;
import vn.iotstar.AloTra.service.impl.UserService;

@RestController
@RequestMapping("/api/cartitems")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CartItemDTO> addCartItem(@RequestBody CartItemDTO request){

        // Lấy thông tin người dùng hiện tại
        UserDTO userDTO = userService.getMyInfo();
        Long user_id = userDTO.getUser_id();  // Lấy user_id từ thông tin người dùng

        CartItemDTO saveCartItem = cartItemService.addCartItem(request, user_id);

        return new ResponseEntity<>(saveCartItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/{cart_item_id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long cart_item_id){

        cartItemService.deleteCartItem(cart_item_id);
        return new ResponseEntity<String>("CartItem have been deleted", HttpStatus.OK);
    }
}
