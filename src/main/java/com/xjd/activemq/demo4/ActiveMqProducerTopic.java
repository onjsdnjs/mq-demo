package com.xjd.activemq.demo4;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author elvis.xu
 * @since 2015-10-03 00:28
 */
public class ActiveMqProducerTopic {
	public static void main(String[] args) throws JMSException {
		String uri = "failover://tcp://103.224.81.184:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(uri);
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		Topic topic = session.createTopic("topic1");
		MessageProducer producer = session.createProducer(topic);
		TextMessage textMessage = session.createTextMessage("HelloWorld4");
		producer.send(textMessage);
		connection.start();
		producer.close();
		session.close();
		connection.close();
	}
}
