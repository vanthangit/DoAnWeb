package vn.iotstar.AloTra.repository;

import vn.iotstar.AloTra.dto.UserDTO;
import vn.iotstar.AloTra.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public UserDTO findByEmail(String email);
}
