package spring.prop.diy;

import spring.prop.Singer2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountryServiceValidator implements ConstraintValidator<CheckCountrySinger, Singer2> {
    @Override
    public void initialize(CheckCountrySinger constraintAnnotation) {

    }

    @Override
    public boolean isValid(Singer2 value, ConstraintValidatorContext context) {
        boolean result=true;
        if (value.getGenre() != null && value.isCountrySinger()
        && (value.getLastName()==null || value.getGender() == null)) {
            result=false;
        }
        return result;
    }
}
