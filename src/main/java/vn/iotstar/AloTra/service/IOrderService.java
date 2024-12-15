package vn.iotstar.AloTra.service;

import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.dto.OrderHistoryDTO;
import vn.iotstar.AloTra.entity.Orders;
import vn.iotstar.AloTra.enums.OrderStatus;

import java.util.Set;

@Service
public interface IOrderService{
    public Set<OrderHistoryDTO> getAllOrders(Long customerId);

    public Set<OrderHistoryDTO> getPurchaseHistory(Long customerId, OrderStatus status);
    
    Set<Orders> getAllOrdersByBranch(Long branchId);
    Set<Orders> getOrdersByStatusAndBranch(OrderStatus status, Long branchId);

}
