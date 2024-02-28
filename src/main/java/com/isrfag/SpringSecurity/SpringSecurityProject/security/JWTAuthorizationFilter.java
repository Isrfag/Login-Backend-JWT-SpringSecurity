package com.isrfag.SpringSecurity.SpringSecurityProject.security;

import com.isrfag.SpringSecurity.SpringSecurityProject.services.IJWTUtilityService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Collections;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    IJWTUtilityService jwtUtilityService;
    public JWTAuthorizationFilter (IJWTUtilityService jwtUtilityService) {
        this.jwtUtilityService=jwtUtilityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if(header==null || !header.startsWith("Bearer ")) { //Si es null o no empieza por bearer

            filterChain.doFilter(request,response);
            return;

        }

        String token = header.substring(7); //hacemos un string quitando el bearer por eso le damos la posicion 7

        try {

            JWTClaimsSet claims = jwtUtilityService.parseJWT(token);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(),null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (InvalidKeySpecException | NoSuchAlgorithmException | ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }

        filterChain.doFilter(request,response);
    }

}
