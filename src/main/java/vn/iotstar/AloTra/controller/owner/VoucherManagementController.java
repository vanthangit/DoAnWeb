package vn.iotstar.AloTra.controller.owner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.AloTra.dto.VoucherDTO;
import vn.iotstar.AloTra.dto.VoucherUpdateDTO;
import vn.iotstar.AloTra.service.IVoucherService;

@Controller
@RequestMapping("/owner")
public class VoucherManagementController {
	@Autowired
	IVoucherService voucherService;

	@GetMapping("/voucher")
	public String getAllVoucher(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "1000") int size, @RequestParam(required = false) String sort,
			@RequestParam(required = false) String search,
			@RequestParam(required = false, defaultValue = "all") String status, Model model) {
		if (sort == null) {
			sort = "none";
		}
		List<VoucherDTO> vouchers;
		Page<VoucherDTO> voucherPage;
		if (search != null && !search.isEmpty()) {
			voucherPage = voucherService.searchVouchers(Long.parseLong(search), page, size, sort);
		} else {
			if ("valid".equals(status)) {
				voucherPage = voucherService.getValidVouchers(page, size, sort);
			} else if ("expired".equals(status)) {
				voucherPage = voucherService.getExpiredVouchers(page, size, sort);
			} else {
				voucherPage = voucherService.getAllVouchers(page, size, sort);
			}
		}
		vouchers = voucherPage.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", voucherPage.getTotalPages());
		model.addAttribute("totalItems", voucherPage.getTotalElements());
		boolean hasPagination = voucherPage.getTotalPages() > 1;
		model.addAttribute("vouchers", vouchers);
		model.addAttribute("hasPagination", hasPagination);
		model.addAttribute("sort", sort);
		model.addAttribute("status", status);
		return "owner/voucher";
	}

	@GetMapping("/voucher/edit/{id}")
	public String showEditVoucherForm(@PathVariable Long id, Model model) {
		VoucherDTO voucherDTO = voucherService.getVoucherById(id);
		model.addAttribute("voucher", voucherDTO); // Add the voucher to the model
		return "owner/edit-voucher"; // Return the view for editing the voucher
	}

	@PostMapping("/voucher/edit/{id}")
	public String updateVoucher(@PathVariable Long id, @ModelAttribute VoucherUpdateDTO voucherDTO, Model model) {
		// Kiểm tra giá trị voucher_value không âm
		if (voucherDTO.getVoucher_value() < 0) {
			model.addAttribute("error", "Giá trị voucher không được âm");
			return "owner/voucher"; // Return the view for editing the voucher
		}

		// Kiểm tra ngày hết hạn không nhỏ hơn ngày bắt đầu
		if (voucherDTO.getEnd_date().isBefore(voucherDTO.getStart_date())) {
			model.addAttribute("error", "Ngày hết hạn không được nhỏ hơn ngày áp dụng");
			return "owner/voucher"; // Return the view for editing the voucher
		}

		VoucherDTO updatevoucherDTO = voucherService.updateVoucher(id, voucherDTO);
		model.addAttribute("voucher", updatevoucherDTO); // Add the voucher to the model
		return "redirect:/owner/voucher"; // Return the view for editing the voucher

	}

	@GetMapping("/voucher/delete/{id}")
	public String deleteVoucherForm(@PathVariable Long id) {
		voucherService.deleteVoucher(id);
		return "redirect:/owner/voucher"; // Return the view for editing the voucher
	}

	@GetMapping("/voucher/add")
	public String showAddVoucherForm(Model model) {
		model.addAttribute("voucher", new VoucherDTO());
		return "owner/add-voucher"; // Hiển thị form thêm mới voucher
	}

	@PostMapping("/voucher/add")
	    public String createVoucher(@ModelAttribute VoucherDTO voucherDTO, Model model) {
	        // Kiểm tra dữ liệu hợp lệ
	        if (voucherDTO.getVoucher_value() < 0) {
	            model.addAttribute("error", "Giá trị voucher không được âm.");
	            return "owner/add-voucher";
	        }
	        if (voucherDTO.getEnd_date() != null && voucherDTO.getStart_date() != null &&
	            voucherDTO.getEnd_date().isBefore(voucherDTO.getStart_date())) {
	            model.addAttribute("error", "Ngày hết hạn không được nhỏ hơn ngày bắt đầu.");
	            return "owner/add-voucher";
	        }

	        // Tạo voucher mới bằng service
	        voucherService.createVoucher(voucherDTO);

	        // Chuyển hướng về danh sách voucher
	        return "redirect:/owner/voucher";
	}

}
