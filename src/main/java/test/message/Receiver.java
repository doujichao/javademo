package test.message;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Receiver {
    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory();
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("my-queue");
        MessageConsumer consumer = session.createConsumer(destination);
        int i=0;
        while (i<3){
            i++;
            TextMessage message= (TextMessage) consumer.receive();
            session.commit();
            System.out.println("收到消息："+message.getText());
        }
        session.close();
        connection.close();
    }
}
