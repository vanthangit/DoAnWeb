package vn.iotstar.AloTra.repository;

import vn.iotstar.AloTra.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.user_id = :user_id")
    Optional<User> findByUserId(@Param("user_id") Long user_id);
}
