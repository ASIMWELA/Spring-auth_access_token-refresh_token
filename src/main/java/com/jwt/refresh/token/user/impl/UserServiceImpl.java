package com.jwt.refresh.token.user.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.refresh.token.role.Role;
import com.jwt.refresh.token.user.User;
import com.jwt.refresh.token.user.UserRepository;
import com.jwt.refresh.token.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new RuntimeException("Wrong credentials")
        );

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role->{
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().name()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),user.isEnabled(),true, true, true,authorities);
    }

    @Override
    public ResponseEntity<User> saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @Override
    public ResponseEntity<User> findByUserName(String userName) {
        return ResponseEntity.ok(userRepository.findByUserName(userName).orElseThrow(()->new RuntimeException("User not found")));
    }

    @Override
    public ResponseEntity.HeadersBuilder<?> deleteUser(String userName) {
        userRepository.findByUserName(userName).ifPresent(userRepository::delete);
        return ResponseEntity.noContent();
    }

}
