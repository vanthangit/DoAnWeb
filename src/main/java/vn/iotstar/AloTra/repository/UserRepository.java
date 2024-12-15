
package vn.iotstar.AloTra.repository;

import vn.iotstar.AloTra.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.role.role_id = :roleId")
    Set<User> findByRoleId(@Param("roleId") Long roleId);

    @Query("SELECT u FROM User u WHERE u.email LIKE %:email% AND u.role.role_id = :roleId")
    Set<User> searchUsersByEmailAndRoleId(@Param("email") String email, @Param("roleId") Long roleId);


    @Query("SELECT u FROM User u WHERE u.user_id = :user_id")
    User findByUserId(@Param("user_id") Long user_id);
}
