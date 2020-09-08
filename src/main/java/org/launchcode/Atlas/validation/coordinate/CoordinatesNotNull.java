package org.launchcode.Atlas.validation.coordinate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = CoordinateValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CoordinatesNotNull {
    String message() default "Must specify Coordinates";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
