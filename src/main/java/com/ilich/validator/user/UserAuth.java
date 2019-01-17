package com.ilich.validator.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;


@Constraint(validatedBy = CheckAuthFields.class)
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAuth {

    String message() default "Wrong input data";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
