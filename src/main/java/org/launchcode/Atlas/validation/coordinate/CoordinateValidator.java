package org.launchcode.Atlas.validation.coordinate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class CoordinateValidator implements ConstraintValidator<CoordinatesNotNull, BigDecimal> {

    @Override
    public void initialize(CoordinatesNotNull constraintAnnotation) {
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        return value != null;
    }
}
