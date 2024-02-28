package com.isrfag.SpringSecurity.SpringSecurityProject.controllers;

import com.isrfag.SpringSecurity.SpringSecurityProject.persistence.entities.UserEntity;
import com.isrfag.SpringSecurity.SpringSecurityProject.services.AuthService;
import com.isrfag.SpringSecurity.SpringSecurityProject.services.models.dtos.LoginDto;
import com.isrfag.SpringSecurity.SpringSecurityProject.services.models.dtos.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth") //Tenemos que ponerle el auth aqui o las rutas no seran públicas
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    private ResponseEntity<ResponseDto> register (@RequestBody UserEntity user) throws Exception {

        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    private ResponseEntity <HashMap<String,String>> login (@RequestBody LoginDto loginRequest) throws Exception {

        HashMap<String, String> login = authService.login(loginRequest);

        if(login.containsKey("jwt")) { //EN LOGINDTO LE DIJIMOS QUE EL CORRECTO LLEVA JWT
            return new ResponseEntity<>(login, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(login, HttpStatus.UNAUTHORIZED);
        }
    }
}
