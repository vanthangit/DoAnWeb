package vn.iotstar.AloTra.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.dto.OrderHistoryDTO;
import vn.iotstar.AloTra.dto.ProductHistoryDTO;
import vn.iotstar.AloTra.entity.Orders;
import vn.iotstar.AloTra.entity.Product;
import vn.iotstar.AloTra.enums.OrderStatus;
import vn.iotstar.AloTra.repository.OrderRepository;
import vn.iotstar.AloTra.service.IOrderService;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderHistoryDTO mapperToPurchaseHistoryDTO(Orders order) {
        Set<ProductHistoryDTO> productHistoryDTOs = order.getOrderLines().stream()
                .map(orderLine -> {
                    Product product = orderLine.getProduct();
                    if (product != null) {
                        return new ProductHistoryDTO(
                                product.getProduct_id(),
                                product.getProduct_name(),
                                orderLine.getQuantity(),
                                product.getImage(),
                                product.getCost(),
                                product.getCategory()
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        // Tính tổng chi phí đơn hàng
        double totalCost = productHistoryDTOs.stream()
                .mapToDouble(product -> product.getCost() * product.getQuantity())
                .sum();
        return new OrderHistoryDTO(
                order.getOrder_id(),
                order.getOrder_date(),
                order.getShipping_address(),
                order.getOrder_status(),
                order.getBranch(),
                productHistoryDTOs,
                totalCost
        );
    }


    @Override
    public Set<OrderHistoryDTO> getAllOrders(Long customerId) {
        Set<OrderHistoryDTO> allOrdersDTOs = new HashSet<>();

        Set<Orders> orders = orderRepository.findOrdersByUserId(customerId);

        for (Orders order : orders) {
            allOrdersDTOs.add(mapperToPurchaseHistoryDTO(order));
        }

        return allOrdersDTOs;
    }

    @Override
    public Set<OrderHistoryDTO> getPurchaseHistory(Long customerId, OrderStatus status) {
        Set<OrderHistoryDTO> historyDTOs = new HashSet<>();

        // Lấy các đơn hàng từ DB dựa vào customerId và trạng thái
        Set<Orders> orders = orderRepository.findOrdersByUserIdAndOrderStatus(customerId, status);

        for (Orders order : orders) {
            historyDTOs.add(mapperToPurchaseHistoryDTO(order));
        }

        return historyDTOs;
    }
}
