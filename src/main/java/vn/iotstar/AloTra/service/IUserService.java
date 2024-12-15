package vn.iotstar.AloTra.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import vn.iotstar.AloTra.entity.User;

@Service
public interface IUserService {
	Set<User> findUsersByRoleId(Long roleId);

	Set<User> searchUsersByEmailAndRoleId(String email, Long roleId);

    void addUser(User user, String branchName, String branchAddress);
    
}