package spring.aop.around;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

public class ProfilingInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        StopWatch sw=new StopWatch();
        sw.start(invocation.getMethod().getName());
        Object returnValue = invocation.proceed();
        sw.stop();
        dumpInfo(invocation,sw.getTotalTimeMillis());
        return returnValue;
    }

    private void dumpInfo(MethodInvocation invocation, long totalTimeMillis) {
        Method method = invocation.getMethod();
        Object target = invocation.getThis();
        Object[] args = invocation.getArguments();
        System.out.println("Executed method:"+method.getName());
        System.out.println("On object of type:"+target.getClass().getName());
        System.out.println("With arguments :");
        for (int i=0;i<args.length;i++){
            System.out.print(" > "+args[i]);
        }
        System.out.println();
        System.out.println("Took :"+totalTimeMillis+" ms");

    }

}
