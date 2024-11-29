package vn.iotstar.AloTra.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import vn.iotstar.AloTra.dto.VoucherDTO;
import vn.iotstar.AloTra.entity.Voucher;

@Mapper
public interface VoucherMapper {

    Voucher toVoucher(VoucherDTO voucherDTO);
    VoucherDTO toVoucherDTO(Voucher voucher);

    void updateVoucher(
            @MappingTarget Voucher voucher, VoucherDTO voucherDTO);
}
