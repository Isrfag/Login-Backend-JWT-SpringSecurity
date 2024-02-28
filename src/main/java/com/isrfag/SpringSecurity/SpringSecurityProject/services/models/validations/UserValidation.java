package com.isrfag.SpringSecurity.SpringSecurityProject.services.models.validations;

import com.isrfag.SpringSecurity.SpringSecurityProject.persistence.entities.UserEntity;
import com.isrfag.SpringSecurity.SpringSecurityProject.services.models.dtos.ResponseDto;

public class UserValidation {
    public ResponseDto validate(UserEntity user) {
        ResponseDto response = new ResponseDto();

        response.setNumOfErrors(0);

        if(user.getFirstName() == null ||
                user.getFirstName().length() < 3 ||
                user.getFirstName().length()>15) {

            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("First name field cannot be null, also it needs to be at least between 3-15 characters");
        }

        if (user.getLastName() == null ||
                user.getLastName().length() < 3 ||
                user.getLastName().length() >30) {

            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("Last name field cannot be null, also it needs to be at least between 3-30 characters");
        }

        if(user.getEmail() == null  ||
                !user.getEmail().matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$") ||
                user.getEmail().length()<3 ) {

            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("Email is not valid");
        }

        if(user.getPassword() == null ||
                !user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")) {

            response.setNumOfErrors(response.getNumOfErrors() + 1);
            response.setMessage("Password must contain at least one digit [0-9], at least one lowercase letter and an uppercase letter, at least" +
                    "one special character and must contain a length between 8 and 20 characters");
        }

        return response;
    }
}
