package vn.iotstar.AloTra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.dto.request.UserCreationRequest;
import vn.iotstar.AloTra.dto.response.UserResponse;
import vn.iotstar.AloTra.entity.User;
import vn.iotstar.AloTra.mapper.UserMapper;
import vn.iotstar.AloTra.repository.UserRepository;
import vn.iotstar.AloTra.service.IUserService;

@Service
public class UserService implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository UserRepository;

    public UserResponse creationUser(UserCreationRequest request){

        User user = userMapper.toUser(request);

        user = UserRepository.save(user);

        return userMapper.toUserResponse(user);
    }
}
