package com.isrfag.SpringSecurity.SpringSecurityProject.services;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

public interface IJWTUtilityService {
    String generateJWT (Long userId) throws IOException,
            InvalidKeySpecException,
            NoSuchAlgorithmException,
            JOSEException;

    JWTClaimsSet parseJWT (String jwt) throws IOException,
            InvalidKeySpecException,
            NoSuchAlgorithmException,
            ParseException,
            JOSEException;
}
