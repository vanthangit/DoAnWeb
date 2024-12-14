package vn.iotstar.AloTra.controller.owner;

import lombok.RequiredArgsConstructor;
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
    public void handleOrders(@RequestBody OrderDTO orderDTO){

        // Lấy thông tin người dùng hiện tại
        UserDTO userDTO = userService.getMyInfo();
        Long user_id = userDTO.getUser_id();  // Lấy user_id từ thông tin người dùng

        orderLineService.cartItemIntoOrderLine(user_id, orderDTO);
    }
}
