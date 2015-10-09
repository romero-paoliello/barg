package com.barganha.barganha.dao;

import com.barganha.barganha.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface UserDao extends JpaRepository<User, Long> {

    User findById(Long id);

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User findByResetPasswordToken(String resetPasswordToken);

}
