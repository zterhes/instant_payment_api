package com.example.shared_lib.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UUIDValidator implements ConstraintValidator<ValidUUID, UUID> {

    @Override
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
        try {
            UUID.fromString(value.toString());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}