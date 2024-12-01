package vn.iotstar.AloTra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.iotstar.AloTra.dto.UserDTO;
import vn.iotstar.AloTra.entity.Role;
import vn.iotstar.AloTra.entity.User;
import vn.iotstar.AloTra.mapper.UserMapper;
import vn.iotstar.AloTra.repository.RoleRepository;
import vn.iotstar.AloTra.repository.UserRepository;
import vn.iotstar.AloTra.service.IUserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);

        Role role = roleRepository.findById(1L).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")  //Kiểm tra trước khi method được thực hiện
    public List<UserDTO> getAllUsers() {
        log.info("In method getAllUsers");
        List<User> users = userRepository.findAll();  // Fetch users from the database
        return users.stream()
                .map(userMapper::toUserDTO)  // Map each User to UserResponse
                .collect(Collectors.toList());
    }


    @PostAuthorize("returnObject.email == authentication.name")  //Kiểm tra sau khi method được thực hiện
    public UserDTO getUserById(Long id) {
        log.info("In method getUserById");
        return userMapper.toUserDTO(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }


    //Lấy user đang đăng nhập
    public UserDTO getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found"));

        return userMapper.toUserDTO(user);
    }
}
