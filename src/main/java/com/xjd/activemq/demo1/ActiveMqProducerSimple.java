package com.xjd.activemq.demo1;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author elvis.xu
 * @since 2015-10-02 22:53
 */
public class ActiveMqProducerSimple {
	public static void main(String[] args) throws JMSException {
		String uri = "failover://tcp://103.224.81.184:61616";

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(uri);
		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Queue queue = session.createQueue("test1");

		MessageProducer producer = session.createProducer(queue);

		TextMessage textMessage = session.createTextMessage();
		textMessage.setText("Hello World");

		producer.send(textMessage);

		connection.close();
	}
}
