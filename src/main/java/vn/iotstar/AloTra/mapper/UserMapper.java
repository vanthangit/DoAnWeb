package vn.iotstar.AloTra.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.iotstar.AloTra.dto.UserDTO;
import vn.iotstar.AloTra.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role.role_id", target = "role_id")
    UserDTO toUserDTO(User user);


    User toUser(UserDTO userDTO);
}
