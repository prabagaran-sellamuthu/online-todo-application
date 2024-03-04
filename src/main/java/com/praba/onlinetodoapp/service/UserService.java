package com.praba.onlinetodoapp.service;

import com.praba.onlinetodoapp.controller.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.praba.onlinetodoapp.entity.User;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
    
    User findById(Long userId);
    
    User save(User user);
    
    void delete(User user);
    
    UserDetails loadUserByUsername(String email);

}
