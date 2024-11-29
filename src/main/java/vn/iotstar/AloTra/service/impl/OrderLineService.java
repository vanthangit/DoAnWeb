package vn.iotstar.AloTra.service.impl;

import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.entity.Cart;
import vn.iotstar.AloTra.entity.CartItem;
import vn.iotstar.AloTra.entity.OrderLine;
import vn.iotstar.AloTra.entity.Orders;
import vn.iotstar.AloTra.mapper.OrderLineMapper;
import vn.iotstar.AloTra.repository.CartRepository;
import vn.iotstar.AloTra.repository.OrderLineRepository;
import vn.iotstar.AloTra.repository.OrderRepository;
import vn.iotstar.AloTra.service.IOrderLineService;
import java.util.Set;

@Service
public class OrderLineService implements IOrderLineService {

    OrderLineRepository orderLineRepository;
    OrderLineMapper orderLineMapper;
    CartRepository cartRepository;
    OrderRepository orderRepository;

    public OrderLineService(OrderLineRepository orderLineRepository, OrderLineMapper orderLineMapper, CartRepository cartRepository, OrderRepository orderRepository) {
        this.orderLineRepository = orderLineRepository;
        this.orderLineMapper = orderLineMapper;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    public void cartItemIntoOrderLine(Long user_id) { // user_id này là user hiện tại đang đăng nhập

        Long cart_id = cartRepository.findCartIdByUserId(user_id); // Đây là cartID của user hiện tại
        Cart cartOfCurrentUser = cartRepository.findById(cart_id).orElseThrow(() -> new RuntimeException("Cart Not Found")); // Đây là cart của user hiện tại

        Set<CartItem> cartItems = cartOfCurrentUser.getCartItems(); // Đây là danh sách cartItem của user hiện tại

        Orders orders = new Orders();
        orders.setUser_id(user_id);
        orderRepository.save(orders); // luu lay id

        for (CartItem cartItem : cartItems) {
            OrderLine orderLine = orderLineMapper.cartItemToOrderLine(cartItem); // Map product va quantity tu cartItem sang Orderline => Chưa map được
            orderLine.setOrders(orders); // Set chung 1 cái order cho các orderline này
            orderLineRepository.save(orderLine); // Lưu orderLine lại
        }
    }
}
