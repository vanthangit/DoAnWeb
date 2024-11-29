package vn.iotstar.AloTra.controller.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iotstar.AloTra.service.impl.OrderLineService;

@RestController
@RequestMapping("/api/orderlines")
@RequiredArgsConstructor
public class OrderLineController {

    private final OrderLineService orderLineService;

    @PostMapping("/{user_id}")
    public void handleOrders(@PathVariable Long user_id){

        orderLineService.cartItemIntoOrderLine(user_id);
    }
}
