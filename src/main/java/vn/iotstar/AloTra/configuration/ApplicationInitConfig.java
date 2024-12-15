package vn.iotstar.AloTra.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.iotstar.AloTra.entity.Role;
import vn.iotstar.AloTra.entity.User;
import vn.iotstar.AloTra.repository.RoleRepository;
import vn.iotstar.AloTra.repository.UserRepository;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig {

    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public ApplicationInitConfig(RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Tạo tài khoản admin
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByEmail("admin@gmail.com").isEmpty()){
                Role role = roleRepository.findById(3L).orElseThrow(() -> new RuntimeException("Role not found"));

                User user = User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(role)
                        .build();
                userRepository.save(user);
                log.warn("Admin user created");
            }
        };
    }
}
