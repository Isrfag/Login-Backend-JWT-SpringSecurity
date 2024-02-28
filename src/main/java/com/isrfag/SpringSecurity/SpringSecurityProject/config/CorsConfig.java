package com.isrfag.SpringSecurity.SpringSecurityProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    //Confuguracion de los CORS para poder usar la API desde el front
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //Para las rutas privadaas
        registry.addMapping("/**") //Todas la url
                .allowedOrigins("http://localhost:4200") //url del frontend
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("Origin","Content-Type","Accept","Authorization")
                .allowCredentials(true)
                .maxAge(3600); //tiempo max

        //Para las rutas publicas
        registry.addMapping("/auth/**")
                .allowedOrigins("http://localhost:4200") //url del frontend , si pones * aceptas todas
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("Origin","Content-Type","Accept","Authorization")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
