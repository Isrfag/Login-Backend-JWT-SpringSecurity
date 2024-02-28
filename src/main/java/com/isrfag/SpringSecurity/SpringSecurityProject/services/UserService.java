package com.isrfag.SpringSecurity.SpringSecurityProject.services;

import com.isrfag.SpringSecurity.SpringSecurityProject.persistence.entities.UserEntity;

import java.util.List;

public interface UserService {

    List<UserEntity> findAllUsers ();
}
