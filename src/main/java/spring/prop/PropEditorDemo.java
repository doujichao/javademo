package spring.prop;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import spring.prop.conf.AppConfig;
import spring.prop.convert.SingerValidationService;

import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PropEditorDemo {

    private GenericApplicationContext context;

    @Before
    public void before(){
//        context=new GenericXmlApplicationContext("classpath:format/format-application.xml");
        context=new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    public void testJSR349(){
        SingerValidationService bean = context.getBean(SingerValidationService.class);
        Singer2 singer2=new Singer2();
        singer2.setFirstName("john");
        singer2.setLastName("mayer");
        singer2.setGenre(Genre.COUNTRY);
        singer2.setGender(null);

        validateSinger(singer2,bean);
    }

    private void validateSinger(Singer2 singer2, SingerValidationService service) {
        Set<ConstraintViolation<Singer2>> violations = service.validateSinger(singer2);
        listViolations(violations);
    }

    private void listViolations(Set<ConstraintViolation<Singer2>> violations) {
        violations.forEach(vio->{
            System.out.println("Validation error for property:"+vio.getPropertyPath()
            +"with value:"+vio.getInvalidValue()+" with error java.message:"+vio.getMessage());
        });
    }

    @Test
    public void testValidator(){
        Singer singer=new Singer();
        singer.setFirstName(null);
        singer.setLastName("Mayer");
        Validator singerValidator = context.getBean("singerValidator", Validator.class);
        BeanPropertyBindingResult result=new BeanPropertyBindingResult(singer,"John");
        ValidationUtils.invokeValidator(singerValidator,singer,result);

        List<ObjectError> errors = result.getAllErrors();
        errors.forEach(e-> System.out.println(e.getCode()));
    }

    @Test
    public void test(){
        Singer eric = context.getBean("eric", Singer.class);
        System.out.println("Eric info:"+eric);
        Singer singer = context.getBean("countrySinger", Singer.class);
        System.out.println("Country info:"+singer);
    }

    @Test
    public void test1(){
        Singer eric = context.getBean("john", Singer.class);
        System.out.println("Eric info:"+eric);
        ConversionService convert = context.getBean("conversionService",ConversionService.class);
        AnotherSinger anotherSinger = convert.convert(eric, AnotherSinger.class);
        System.out.println("anotherSinger:--------"+anotherSinger);
        String[] strings = convert.convert("a,b,c", String[].class);
        System.out.println(Arrays.toString(strings));
    }

    @After
    public void after(){
        context.close();
    }

}
