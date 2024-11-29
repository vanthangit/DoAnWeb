package vn.iotstar.AloTra.service.impl;

import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.dto.VoucherDTO;
import vn.iotstar.AloTra.entity.Voucher;
import vn.iotstar.AloTra.mapper.VoucherMapper;
import vn.iotstar.AloTra.repository.VoucherRepository;
import vn.iotstar.AloTra.service.IVoucherService;

import java.time.LocalDate;

@Service
public class VoucherService implements IVoucherService {

    VoucherRepository voucherRepository;
    VoucherMapper voucherMapper;

    public VoucherService(VoucherRepository voucherRepository, VoucherMapper voucherMapper) {
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
    }

    public VoucherDTO createVoucher(VoucherDTO voucherDTO) {

        Voucher voucher = voucherMapper.toVoucher(voucherDTO);
        LocalDate currentDate = null;

        if (voucherDTO.getStart_date() == null) {
            currentDate = LocalDate.now();
        }
        voucher.setStart_date(currentDate);
        voucherRepository.save(voucher);
        return voucherMapper.toVoucherDTO(voucher);
    }

    public VoucherDTO updateVoucher(Long voucher_id ,VoucherDTO voucherDTO) {

        Voucher voucher = voucherRepository.findById(voucher_id).orElseThrow(() -> new RuntimeException("Voucher Not Found"));

        voucherMapper.updateVoucher(voucher, voucherDTO);

        if (voucherDTO.getStart_date() == null) {
            voucher.setStart_date(LocalDate.now());
        }
        voucherRepository.save(voucher);
        return voucherMapper.toVoucherDTO(voucher);
    }

    public void deleteVoucher(Long voucher_id) {
        voucherRepository.deleteById(voucher_id);
    }
}
