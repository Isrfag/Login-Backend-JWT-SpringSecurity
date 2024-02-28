package com.isrfag.SpringSecurity.SpringSecurityProject.persistence.repositories;

import com.isrfag.SpringSecurity.SpringSecurityProject.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    @Query(value ="SELECT * FROM user WHERE email = :email",nativeQuery = true)
    Optional<UserEntity> findByEmail(String email);
    //nativequery es que puedas meterle SQL puro sin ser HQL, : es el parametro que le pasamos debajo a la funcion
}
