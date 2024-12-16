package vn.iotstar.AloTra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iotstar.AloTra.dto.VoucherDTO;
import vn.iotstar.AloTra.dto.VoucherUpdateDTO;
import vn.iotstar.AloTra.service.IVoucherService;
import vn.iotstar.AloTra.service.impl.VoucherService;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
public class VoucherController {

    private final IVoucherService voucherService;

    @PostMapping
    public ResponseEntity<VoucherDTO> createVoucher(@RequestBody VoucherDTO voucherDTO) {

        VoucherDTO voucherSave = voucherService.createVoucher(voucherDTO);
        return new ResponseEntity<>(voucherSave, HttpStatus.CREATED);
    }

    @PutMapping("/{voucher_id}")
    public ResponseEntity<VoucherDTO> updateVoucher(@PathVariable Long voucher_id, @RequestBody VoucherUpdateDTO voucherDTO) {

        VoucherDTO voucherSave = voucherService.updateVoucher(voucher_id, voucherDTO);
        return new ResponseEntity<>(voucherSave, HttpStatus.OK);
    }

    @DeleteMapping("/{voucher_id}")
    public ResponseEntity<String> deleteVoucher(@PathVariable Long voucher_id){

        voucherService.deleteVoucher(voucher_id);
        return new ResponseEntity<String>("Voucher have been deleted", HttpStatus.OK);
    }

    @GetMapping("/loadAllVouchers")
    public ResponseEntity<List<VoucherDTO>> getAllVouchers(){

        return new ResponseEntity<>(voucherService.loadAllVoucher(), HttpStatus.OK);
    }
}
