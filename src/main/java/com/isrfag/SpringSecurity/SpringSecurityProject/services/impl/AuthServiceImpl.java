package com.isrfag.SpringSecurity.SpringSecurityProject.services.impl;

import com.isrfag.SpringSecurity.SpringSecurityProject.persistence.entities.UserEntity;
import com.isrfag.SpringSecurity.SpringSecurityProject.persistence.repositories.UserRepository;
import com.isrfag.SpringSecurity.SpringSecurityProject.services.AuthService;
import com.isrfag.SpringSecurity.SpringSecurityProject.services.IJWTUtilityService;
import com.isrfag.SpringSecurity.SpringSecurityProject.services.models.dtos.LoginDto;
import com.isrfag.SpringSecurity.SpringSecurityProject.services.models.dtos.ResponseDto;
import com.isrfag.SpringSecurity.SpringSecurityProject.services.models.validations.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

     @Autowired
     private IJWTUtilityService jwtUtilityService;

     @Autowired
    private UserValidation userValidation;

     @Override
     public HashMap<String,String> login (LoginDto login) throws Exception {

         try{

             //Creamos el jwt
             HashMap<String,String> jwt = new HashMap<>();
             Optional<UserEntity> user = userRepository.findByEmail(login.getEmail());
             if(user.isEmpty()) {
                 jwt.put("error", "User not registered!");
                 return jwt;
             }

             if(verifyPassword(login.getPassword(), user.get().getPassword())) {
                 jwt.put("jwt", jwtUtilityService.generateJWT(user.get().getId()));
             }else {
                 jwt.put("error","Authentication failed");
             }
             return jwt;

         }catch (Exception e) {

             throw new Exception (e.toString());
         }
     }

     @Override
     public ResponseDto register (UserEntity user) throws Exception {

         try{

             ResponseDto response = userValidation.validate(user);

             if(response.getNumOfErrors() > 0) /*Si no hay errores*/ {
                 return response;
             }

             //Buscamos todos los usuarios para ver si esta registrado o no pero solo si pasa validaciones
             List<UserEntity> getAllUsers = userRepository.findAll();

             for (UserEntity repeatFields: getAllUsers) {

                 if (repeatFields.getEmail().equals(user.getEmail())) {
                     response.setMessage("User already exist!");
                     return response;
                 }

             }

             //Vamos a encriptar la contraseña
             BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); //El numero es la dureza
             user.setPassword(encoder.encode(user.getPassword()));
             userRepository.save(user); //Y guardamos el usuario
             response.setMessage("User created succesfully!");

             return response;


         }catch (Exception e) {
             throw new Exception(e.toString());
         }

     }

    //Verificar la contraseña

    private boolean verifyPassword(String enteredPassword, String sotoredPassword) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, sotoredPassword); //Prueban si la que mete y la guardada hacen match
    }
}
