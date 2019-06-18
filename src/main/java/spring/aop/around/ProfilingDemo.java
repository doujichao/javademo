package spring.aop.around;

import org.springframework.aop.framework.ProxyFactory;

public class ProfilingDemo {
    public static void main(String[] args) {
        WorkBean bean = getWorkerBean();
        bean.doSomeWork(100);
    }

    private static WorkBean getWorkerBean() {
        WorkBean target = new WorkBean();
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(target);
        pf.addAdvice(new ProfilingInterceptor());
        return (WorkBean) pf.getProxy();
    }
}
