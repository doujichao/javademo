package spring.aop.proxyfactory;

import org.aopalliance.aop.Advice;
import org.aspectj.lang.JoinPoint;

public class AuditAdvice implements Advice {

    public void simpleBeforeAdvice(JoinPoint joinPoint){
        System.out.println("Executing: " +
                joinPoint.getSignature().getDeclaringTypeName()
        +" "+joinPoint.getSignature().getName());
    }
}
