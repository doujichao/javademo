package test;

public class MessageFactoryImpl implements MessageFactory{

    private MessageFactoryImpl (){}

    private static final Message message=MessageImpl.getInstance();

    @Override
    public Message newMessage(String countryCode) {
        return message;
    }
}
