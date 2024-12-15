package vn.iotstar.AloTra.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.iotstar.AloTra.dto.VoucherDTO;
import vn.iotstar.AloTra.dto.VoucherUpdateDTO;
import vn.iotstar.AloTra.entity.Voucher;

@Mapper
public interface VoucherMapper {

	Voucher toVoucher(VoucherDTO voucherDTO);

    @Mapping(target = "order_id", source = "order.order_id")
    VoucherDTO toVoucherDTO(Voucher voucher);

    void updateVoucher(
            @MappingTarget Voucher voucher, VoucherUpdateDTO voucherDTO);
}
