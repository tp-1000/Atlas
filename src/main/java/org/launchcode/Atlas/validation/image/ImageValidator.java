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
        //okay if there isn't an image file uploaded, app wont break, js required though so may never be experienced
        if(value == null || value.isEmpty()){
            return true;
        }
            String fileName = value.getOriginalFilename();
            return Pattern.matches("[^\\s].+\\.((jpeg)|(png)|(gif)|(jpg))", fileName);
    }
}
