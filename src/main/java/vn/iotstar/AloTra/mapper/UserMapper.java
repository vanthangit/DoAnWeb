package vn.iotstar.AloTra.mapper;

import org.mapstruct.Mapper;
import vn.iotstar.AloTra.dto.UserDTO;
import vn.iotstar.AloTra.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);
    UserDTO toDto(User user);
}
