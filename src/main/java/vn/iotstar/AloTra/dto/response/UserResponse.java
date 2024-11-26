package vn.iotstar.AloTra.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.iotstar.AloTra.entity.Role;
import vn.iotstar.AloTra.enums.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String full_name;
    private String phone;
    private Gender gender;
    private String address;
    private String email;
    private String password;
    private Role role;
}
