package com.ilich.validator.advert;

import com.ilich.model.AdvertInfoData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


class CheckAdvertFields implements ConstraintValidator<AdvertInfo, AdvertInfoData> {

    @Override
    public void initialize(AdvertInfo constraintAnnotation) {
    }

    @Override
    public boolean isValid(AdvertInfoData aid, ConstraintValidatorContext constraintValidatorContext) {
        return aid.getCompany().length() > 2 && aid.getCompany().length() < 25 &&
                aid.getModel().length() > 1 && aid.getModel().length() < 25 &&
                aid.getColor().length() > 1 && aid.getColor().length() < 25 &&
                aid.getYearOfIssue() > 1930 && aid.getYearOfIssue() < 2019 &&
                aid.getPrice() > 100 && aid.getPrice() < 999999;
    }
}
