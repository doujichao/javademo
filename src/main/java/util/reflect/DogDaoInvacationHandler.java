package util.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DogDaoInvacationHandler implements InvocationHandler {
    private DagDao dao;

    public DogDaoInvacationHandler(DagDao dao){
        this.dao=dao;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("这是一条狗");
        return method.invoke(dao, args);
    }
}
