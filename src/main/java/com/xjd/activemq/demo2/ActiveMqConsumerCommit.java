package com.xjd.activemq.demo2;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author elvis.xu
 * @since 2015-10-02 23:34
 */
public class ActiveMqConsumerCommit {
	public static void main(String[] args) throws JMSException {
		String uri = "failover://tcp://103.224.81.184:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(uri);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
		Queue queue = session.createQueue("test1");
		MessageConsumer consumer = session.createConsumer(queue);
		Message message = consumer.receive();

		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			System.out.println(textMessage.getText());
		}

		session.rollback();
//		session.commit();
		connection.close();
	}
}
