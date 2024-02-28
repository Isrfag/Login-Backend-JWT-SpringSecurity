package com.isrfag.SpringSecurity.SpringSecurityProject.services.impl;

import com.isrfag.SpringSecurity.SpringSecurityProject.persistence.entities.UserEntity;
import com.isrfag.SpringSecurity.SpringSecurityProject.persistence.repositories.UserRepository;
import com.isrfag.SpringSecurity.SpringSecurityProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserEntity> findAllUsers() {
         return userRepository.findAll();
    }
}
