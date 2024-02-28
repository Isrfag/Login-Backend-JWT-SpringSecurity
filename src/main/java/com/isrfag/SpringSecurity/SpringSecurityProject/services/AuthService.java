package com.isrfag.SpringSecurity.SpringSecurityProject.services;

import com.isrfag.SpringSecurity.SpringSecurityProject.persistence.entities.UserEntity;
import com.isrfag.SpringSecurity.SpringSecurityProject.services.models.dtos.LoginDto;
import com.isrfag.SpringSecurity.SpringSecurityProject.services.models.dtos.ResponseDto;

import java.util.HashMap;

public interface AuthService {

    HashMap<String,String> login (LoginDto login) throws Exception;

    ResponseDto register (UserEntity user) throws Exception;
}
