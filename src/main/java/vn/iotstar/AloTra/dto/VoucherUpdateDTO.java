package vn.iotstar.AloTra.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoucherUpdateDTO {
	private Double voucher_value;
    private LocalDate start_date;
    private LocalDate end_date;
}
