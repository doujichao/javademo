package java.message;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Sender {
    public static void main(String[] args) throws JMSException, InterruptedException {
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory();
        Connection connection =
                connectionFactory.createConnection();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("my-queue");
        MessageProducer producer = session.createProducer(destination);
        for(int i=0;i<3;i++){
            TextMessage message = session.createTextMessage("大家好，这是一个测试");
            Thread.sleep(1000);
            //通过消息生产者发出消息
            producer.send(message);
        }
        session.commit();
        session.close();
        connection.close();
    }
}
