package vn.iotstar.AloTra.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements IUserService {

	UserRepository userRepository;
	RoleRepository roleRepository;
	UserMapper userMapper;
	PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper,
			@Lazy PasswordEncoder passwordEncoder) {
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

	public void saveUserInformation(String name, String email) {
		User user = new User();
		user.setEmail(email);
		user.setFull_name(name);
		Role role = roleRepository.findById(1L).orElseThrow(() -> new RuntimeException("Role not found"));
		user.setRole(role);

		var existingUser = userRepository.findByEmail(email);
		if (existingUser.isEmpty()) {
			userRepository.save(user);
		}
	}

	@PreAuthorize("hasRole('ADMIN')") // Kiểm tra trước khi method được thực hiện
	public List<UserDTO> getAllUsers() {
		log.info("In method getAllUsers");
		List<User> users = userRepository.findAll(); // Fetch users from the database
		return users.stream().map(userMapper::toUserDTO) // Map each User to UserResponse
				.collect(Collectors.toList());
	}

	@PostAuthorize("returnObject.email == authentication.name") // Kiểm tra sau khi method được thực hiện
	public UserDTO getUserById(Long id) {
		log.info("In method getUserById");
		return userMapper
				.toUserDTO(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
	}

	// Lấy user đang đăng nhập
	public UserDTO getMyInfo() {
		var context = SecurityContextHolder.getContext();
		String email = context.getAuthentication().getName();

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		return userMapper.toUserDTO(user);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void updateUser(Long id, UserDTO userDTO) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng"));
		existingUser.setAddress(userDTO.getAddress());
		existingUser.setEmail(userDTO.getEmail());
		existingUser.setFull_name(userDTO.getFull_name());
		existingUser.setGender(userDTO.getGender());
		existingUser.setPhone(userDTO.getPhone());
		userRepository.save(existingUser);
	}

	public User findById(Long id) {
		return userRepository.findByUserId(id);
	}

}
