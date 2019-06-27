package spring.prop.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.prop.Singer2;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service("singerValidationService")
public class SingerValidationService {

    @Autowired
    private Validator validator;

    public Set<ConstraintViolation<Singer2>> validateSinger(Singer2 singer2){
        return validator.validate(singer2);
    }
}
