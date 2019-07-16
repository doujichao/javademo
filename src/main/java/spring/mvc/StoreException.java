package spring.mvc;

public class StoreException extends Exception {

    public StoreException(){
        this("StoreException");
    }

    public StoreException(String msg) {
        super(msg);
    }
}
