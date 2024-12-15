package vn.iotstar.AloTra.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.AloTra.dto.OrderDTO;
import vn.iotstar.AloTra.dto.UserDTO;
import vn.iotstar.AloTra.service.impl.OrderLineService;
import vn.iotstar.AloTra.service.impl.UserService;

@RestController
@RequestMapping("/api/orderlines")
@RequiredArgsConstructor
public class OrderLineController {

    private final OrderLineService orderLineService;
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<OrderDTO> handleOrders(@RequestBody OrderDTO orderDTO){

        // Lấy thông tin người dùng hiện tại
        UserDTO userDTO = userService.getMyInfo();
        Long user_id = userDTO.getUser_id();  // Lấy user_id từ thông tin người dùng

        OrderDTO savedOrderDTO = orderLineService.cartItemIntoOrderLine(user_id, orderDTO);

        return ResponseEntity.ok(savedOrderDTO);
    }
}
