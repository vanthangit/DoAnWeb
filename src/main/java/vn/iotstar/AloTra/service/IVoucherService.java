package vn.iotstar.AloTra.service;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import vn.iotstar.AloTra.dto.VoucherDTO;
import vn.iotstar.AloTra.dto.VoucherUpdateDTO;
import vn.iotstar.AloTra.entity.Voucher;


@Service
public interface IVoucherService  {
    public Page<VoucherDTO> getAllVouchers(int page, int size, String sort);

    public VoucherDTO getVoucherById(Long id);

	void deleteVoucher(Long voucher_id);

	VoucherDTO updateVoucher(Long voucher_id, VoucherUpdateDTO voucherDTO);

	List<VoucherDTO> loadAllVoucher();

	VoucherDTO createVoucher(VoucherDTO voucherDTO);

	Page<VoucherDTO> getExpiredVouchers(int page, int size, String sort);

	Page<VoucherDTO> getValidVouchers(int page, int size, String sort);

	Page<VoucherDTO> searchVouchers(Long search, int page, int size, String sort);

	
}