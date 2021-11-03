package com.jwt.refresh.token.user;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {
    ResponseEntity<User> saveUser(User user);
    ResponseEntity<List<User>> getAllUsers();
    ResponseEntity<User> findByUserName(String userName);
    ResponseEntity.HeadersBuilder<?> deleteUser(String userName);
}
