package vn.iotstar.AloTra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.AloTra.dto.OrderDTO;
import vn.iotstar.AloTra.entity.*;
import vn.iotstar.AloTra.enums.OrderStatus;
import vn.iotstar.AloTra.enums.PaymentStatus;
import vn.iotstar.AloTra.mapper.OrderLineMapper;
import vn.iotstar.AloTra.repository.*;
import vn.iotstar.AloTra.service.IOrderLineService;

import java.util.Date;
import java.util.Set;

@Service
@Slf4j
public class OrderLineService implements IOrderLineService {

    CartItemRepository cartItemRepository;
    PaymentRepository paymentRepository;
    OrderLineRepository orderLineRepository;
    OrderLineMapper orderLineMapper;
    CartRepository cartRepository;
    OrderRepository orderRepository;
    BranchRepository branchRepository;

    public OrderLineService(OrderLineRepository orderLineRepository, OrderLineMapper orderLineMapper, CartRepository cartRepository, OrderRepository orderRepository, BranchRepository branchRepository, PaymentRepository paymentRepository, CartItemRepository cartItemRepository) {
        this.orderLineRepository = orderLineRepository;
        this.orderLineMapper = orderLineMapper;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.branchRepository = branchRepository;
        this.paymentRepository = paymentRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public void cartItemIntoOrderLine(Long user_id, OrderDTO orderDTO) { // user_id này là user hiện tại đang đăng nhập

        Long cart_id = cartRepository.findCartIdByUserId(user_id); // Đây là cartID của user hiện tại

        Cart cart = cartRepository.findById(cart_id).orElseThrow(() -> new RuntimeException("Cart not found!"));

        Cart cartOfCurrentUser = cartRepository.findById(cart_id).orElseThrow(() -> new RuntimeException("Cart Not Found")); // Đây là cart của user hiện tại

        Set<CartItem> cartItems = cartOfCurrentUser.getCartItems(); // Đây là danh sách cartItem của user hiện tại

        // Xu li thêm vào bảng Order và OrderLine
        log.info("Branch_name: {}", orderDTO.getBranch_name());
        Branch branch = branchRepository.findByBranchName(orderDTO.getBranch_name());
        Orders orders = new Orders();
        orders.setUser_id(user_id);
        orders.setBranch(branch);
        orders.setShipping_address(orderDTO.getShipping_address());
        orders.setOrder_status(OrderStatus.PENDING);
        orders.setOrder_date(new java.sql.Date(System.currentTimeMillis()));
        orders.setDelivery_date(new java.sql.Date(System.currentTimeMillis()));
        orderRepository.save(orders); // luu lay id
        for (CartItem cartItem : cartItems) {
            OrderLine orderLine = orderLineMapper.cartItemToOrderLine(cartItem); // Map product va quantity tu cartItem sang Orderline => Chưa map được
            orderLine.setOrders(orders); // Set chung 1 cái order cho các orderline này
            orderLineRepository.save(orderLine); // Lưu orderLine lại
        }

        // Xử lí thêm vào bảng Payment
        Payment payment = new Payment();
        payment.setOrder(orders);
        payment.setTotal(orderDTO.getTotal_amount());
        payment.setPayment_method(orderDTO.getPayment_method());
        if (orderDTO.getPayment_method() == "VNPAY"){
            payment.setPayment_status(PaymentStatus.PAID);
        }
        else {
            payment.setPayment_status(PaymentStatus.PENDING);
        }
        payment.setPayment_date(new java.sql.Date(System.currentTimeMillis()));
        paymentRepository.save(payment);

        // Xóa cart khi người dùng mua hàng rồi
        cartItemRepository.deleteByCart(cart);
    }
}
