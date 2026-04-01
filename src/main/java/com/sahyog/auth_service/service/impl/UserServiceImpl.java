package com.sahyog.auth_service.service.impl;

import com.sahyog.auth_service.dto.request.RegisterRequest;
import com.sahyog.auth_service.entity.User;
import com.sahyog.auth_service.repository.UserRepository;
import com.sahyog.auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly=true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByEmailOrUserName(usernameOrEmail,usernameOrEmail);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUserName(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User createUser(RegisterRequest request) {
        User obj=new User();
        obj.setUserName(request.getUsername());
        obj.setEmail(request.getEmail());
        obj.setPhone(request.getPhoneNumber());
        String hashedPassword=passwordEncoder.encode(request.getPassword());
        obj.setPassword(hashedPassword);
        return userRepository.save(obj);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
