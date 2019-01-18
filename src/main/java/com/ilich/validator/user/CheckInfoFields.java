package com.ilich.validator.user;

import com.ilich.model.UserInfoData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.lang.String.valueOf;


class CheckInfoFields implements ConstraintValidator<UserInfo, UserInfoData> {

    @Override
    public void initialize(UserInfo constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserInfoData uid, ConstraintValidatorContext constraintValidatorContext) {
        return uid.getName().length() > 2 && uid.getName().length() < 25 &&
                uid.getCity().length() > 3 && uid.getCity().length() < 25 &&
                valueOf(uid.getPhone()).length() == 9;
    }
}
