package org.launchcode.Atlas.validation.image;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = ImageValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImage {
    String message() default "Please select an image of type: (jpeg, jpg, png, gif)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
