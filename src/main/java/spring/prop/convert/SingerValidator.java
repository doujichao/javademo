package spring.prop.convert;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import spring.prop.Singer;

@Component("singerValidator")
public class SingerValidator implements Validator {
    /**
     * 是否支持验证传入的类型
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Singer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors,"firstName",
                "firstName.empty");
    }
}
