package org.com.itpple.spot.server.global.common.validator;

import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private EnumValid annotation;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues == null) {
            return false;
        }

        return Arrays.stream(enumValues)
            .anyMatch(enumValue -> value.equals(String.valueOf(enumValue)));
    }
}
