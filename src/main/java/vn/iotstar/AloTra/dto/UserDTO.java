package vn.iotstar.AloTra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iotstar.AloTra.enums.Gender;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private Long user_id;
    private String full_name;
    private String phone;
    private Gender gender;
    private String address;
    private String email;
    private String password;
    private String confirmPassword;
    private Long role_id;
    private Long branch_id;
}
