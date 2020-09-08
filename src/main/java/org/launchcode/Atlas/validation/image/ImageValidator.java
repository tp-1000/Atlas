package org.launchcode.Atlas.validation.image;

import org.hibernate.validator.constraintvalidators.RegexpURLValidator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.regex.Pattern;

public class ImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    @Override
    public void initialize(ValidImage constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        String fileName = value.getOriginalFilename();

        return Pattern.matches("[^.]*\\.((jpeg)|(png)|(gif)|(jpg))", fileName);
    }
}
