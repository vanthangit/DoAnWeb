package vn.iotstar.AloTra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.dto.UserDTO;
import vn.iotstar.AloTra.entity.Cart;
import vn.iotstar.AloTra.entity.Role;
import vn.iotstar.AloTra.entity.User;
import vn.iotstar.AloTra.mapper.UserMapper;
import vn.iotstar.AloTra.repository.CartRepository;
import vn.iotstar.AloTra.repository.RoleRepository;
import vn.iotstar.AloTra.repository.UserRepository;
import vn.iotstar.AloTra.service.IUserService;

@Service
@Slf4j
public class UserService implements IUserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;

    CartRepository cartRepository;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.cartRepository = cartRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {

        User user = new User();
        //Map tay
        user.setFull_name(userDTO.getFull_name());
        user.setPhone(userDTO.getPhone());
        user.setGender(userDTO.getGender());
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        Role role = roleRepository.findById(1L).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);

        //Xu li them cart cho user
        Cart newCart = new Cart();
        newCart.setUser(user);
        cartRepository.save(newCart);

        return userMapper.toUserDTO(user);
    }
}
