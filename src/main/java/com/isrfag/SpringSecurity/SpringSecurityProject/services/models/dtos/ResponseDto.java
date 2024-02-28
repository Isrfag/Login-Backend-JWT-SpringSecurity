package com.isrfag.SpringSecurity.SpringSecurityProject.services.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

    private int numOfErrors;
    private String message;
}
