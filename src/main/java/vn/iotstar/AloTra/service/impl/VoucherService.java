package vn.iotstar.AloTra.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.dto.VoucherDTO;
import vn.iotstar.AloTra.dto.VoucherUpdateDTO;
import vn.iotstar.AloTra.entity.Voucher;
import vn.iotstar.AloTra.mapper.VoucherMapper;
import vn.iotstar.AloTra.repository.VoucherRepository;
import vn.iotstar.AloTra.service.IVoucherService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoucherService implements IVoucherService {

	VoucherRepository voucherRepository;
	VoucherMapper voucherMapper;

	public VoucherService(VoucherRepository voucherRepository, VoucherMapper voucherMapper) {
		this.voucherRepository = voucherRepository;
		this.voucherMapper = voucherMapper;
	}

	@Override
	public List<VoucherDTO> loadAllVoucher() {
		List<Voucher> vouchers = voucherRepository.findAll();
		return vouchers.stream()
				.map(voucherMapper::toVoucherDTO)
				.collect(Collectors.toList());
	}


	@Override
	public VoucherDTO createVoucher(VoucherDTO voucherDTO) {

		Voucher voucher = voucherMapper.toVoucher(voucherDTO);

		if (voucherDTO.getStart_date() == null) {
			voucher.setStart_date(LocalDate.now());
		}
		voucherRepository.save(voucher);
		return voucherMapper.toVoucherDTO(voucher);
	}

	@Override
	public VoucherDTO updateVoucher(Long voucher_id, VoucherUpdateDTO voucherDTO) {

		Voucher voucher = voucherRepository.findById(voucher_id)
				.orElseThrow(() -> new RuntimeException("Voucher Not Found"));

		voucherMapper.updateVoucher(voucher, voucherDTO);

		if (voucherDTO.getStart_date() == null) {
			voucher.setStart_date(LocalDate.now());
		}
		voucherRepository.save(voucher);
		return voucherMapper.toVoucherDTO(voucher);
	}

	@Override
	public void deleteVoucher(Long voucher_id) {
		voucherRepository.deleteById(voucher_id);
	}

	@Override
	public Page<VoucherDTO> getAllVouchers(int page, int size, String sort) {
		Pageable pageable;
		Page<Voucher> vouchersPage;
		if ("none".equals(sort)) {
			pageable = PageRequest.of(page, size);
		} else {
			Sort.Direction direction = "desc".equalsIgnoreCase(sort) ? Sort.Direction.DESC : Sort.Direction.ASC;
			pageable = PageRequest.of(page, size, Sort.by(direction, "voucher_value"));
		}
		vouchersPage = voucherRepository.findAll(pageable);
		return vouchersPage.map(voucherMapper::toVoucherDTO);
	}
	@Override
	public Page<VoucherDTO> getValidVouchers(int page, int size, String sort) {
		Pageable pageable;
		Page<Voucher> vouchersPage;
		if ("none".equals(sort)) {
			pageable = PageRequest.of(page, size);
		} else {
			Sort.Direction direction = "desc".equalsIgnoreCase(sort) ? Sort.Direction.DESC : Sort.Direction.ASC;
			pageable = PageRequest.of(page, size, Sort.by(direction, "voucher_value"));
		}
		vouchersPage = voucherRepository.findValidVouchers(LocalDate.now(),pageable);
		return vouchersPage.map(voucherMapper::toVoucherDTO);
	}
	@Override
	public Page<VoucherDTO> getExpiredVouchers(int page, int size, String sort) {
		Pageable pageable;
		Page<Voucher> vouchersPage;
		if ("none".equals(sort)) {
			pageable = PageRequest.of(page, size);
		} else {
			Sort.Direction direction = "desc".equalsIgnoreCase(sort) ? Sort.Direction.DESC : Sort.Direction.ASC;
			pageable = PageRequest.of(page, size, Sort.by(direction, "voucher_value"));
		}
		vouchersPage = voucherRepository.findExpiredVouchers(LocalDate.now(),pageable);
		return vouchersPage.map(voucherMapper::toVoucherDTO);
	}

	@Override
	public VoucherDTO getVoucherById(Long id) {
		return voucherRepository.findById(id).map(voucherMapper::toVoucherDTO).orElse(null);
	}
	
	@Override
	public Page<VoucherDTO> searchVouchers(Long search, int page, int size, String sort) {
		Optional<Voucher> voucher;
		voucher = voucherRepository.findById(search);
		if (voucher.isPresent()) {
	        // Tạo một danh sách chỉ chứa một phần tử VoucherDTO
	        List<VoucherDTO> voucherDTOList = Collections.singletonList(voucherMapper.toVoucherDTO(voucher.get()));
	        
	        // Tạo Pageable từ page và size
	        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

	        // Tạo và trả về một Page chứa một phần tử
	        return new PageImpl<>(voucherDTOList, pageable, 1);
	    } else {
	        // Nếu không tìm thấy voucher, trả về một Page rỗng
	        return new PageImpl<>(Collections.emptyList(), PageRequest.of(page, size), 0);
	    }
	}


}
