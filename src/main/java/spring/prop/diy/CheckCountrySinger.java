package spring.prop.diy;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = CountryServiceValidator.class)
@Documented
public @interface CheckCountrySinger {
    /**
     * 属性定义违反约束条件时返回的消息
     * @return
     */
    String message() default "Country Singer should have gender and " +
            "last name defined";

    /**
     * 属性指定使用的验证组
     * @return
     */
    Class<?>[] groups() default {};

    /**
     * 属性指定其他有效荷载对象
     * @return
     */
    Class<? extends Payload>[] payload() default {} ;
}
