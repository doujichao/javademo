package test;

public class MessageImpl implements Message{
    private MessageImpl(){}

    private final static Message message=new MessageImpl();

    public static Message getInstance(){
        return message;
    }

    @Override
    public void printMessage() {

    }
}
