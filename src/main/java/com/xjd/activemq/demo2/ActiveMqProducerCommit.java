package com.xjd.activemq.demo2;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author elvis.xu
 * @since 2015-10-02 23:26
 */
public class ActiveMqProducerCommit {
	public static void main(String[] args) throws JMSException {
		String uri = "failover://tcp://103.224.81.184:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(uri);
		Connection connection = connectionFactory.createConnection();
		Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
		Queue queue = session.createQueue("test1");
		MessageProducer producer = session.createProducer(queue);
		TextMessage textMessage = session.createTextMessage("Hello World2");
		producer.send(textMessage);
		session.commit();
		textMessage = session.createTextMessage("Hello World2-1");
		producer.send(textMessage);
		session.commit();
		connection.close();
	}
}
