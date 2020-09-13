package org.launchcode.Atlas.validation.image;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    @Override
    public void initialize(ValidImage constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()){
            return true;
        }
            String fileName = value.getOriginalFilename();
            return Pattern.matches("[^\\s]+\\.((jpeg)|(png)|(gif)|(jpg))", fileName);
    }
}
