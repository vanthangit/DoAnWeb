package vn.iotstar.AloTra.mapper;

import org.mapstruct.Mapper;
import vn.iotstar.AloTra.dto.request.UserCreationRequest;
import vn.iotstar.AloTra.dto.response.UserResponse;
import vn.iotstar.AloTra.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);
}
