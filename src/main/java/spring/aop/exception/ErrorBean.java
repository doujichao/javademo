package spring.aop.exception;

public class ErrorBean {

    public void errorProneMethod() throws Exception {
        throw new Exception("Generic Exception");
    }

    public void otherErrorProneMethod(){
        throw new IllegalArgumentException("IllegalArgument Exception");
    }
}
