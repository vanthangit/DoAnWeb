package vn.iotstar.AloTra.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoucherDTO {
	private Long voucher_id;
    private Double voucher_value;
    private LocalDate start_date;
    private LocalDate end_date;
    private Long order_id;
}
