package vn.iotstar.AloTra.mapper;

import org.mapstruct.Mapper;
import vn.iotstar.AloTra.dto.OrderDTO;
import vn.iotstar.AloTra.entity.Orders;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toOrderDTO(Orders orders);
}
