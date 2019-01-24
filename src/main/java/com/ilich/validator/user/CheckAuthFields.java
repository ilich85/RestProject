package com.ilich.validator.user;

import com.ilich.model.UserAuthData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CheckAuthFields implements ConstraintValidator<UserAuth, UserAuthData> {

    @Override
    public void initialize(UserAuth constraintAnnotation) {}

    @Override
    public boolean isValid(UserAuthData authData, ConstraintValidatorContext constraintValidatorContext) {
        return authData.getUsername().length() > 5 && authData.getUsername().length() < 25 &&
                authData.getPassword().length() > 5 && authData.getPassword().length() < 25;
    }
}
