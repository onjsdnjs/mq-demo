package com.xjd.activemq.demo4;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author elvis.xu
 * @since 2015-10-03 00:33
 */
public class ActiveMqSubscribeTopic {
	public static void main(String[] args) throws JMSException {
		String uri = "failover://tcp://103.224.81.184:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(uri);
		Connection connection = connectionFactory.createConnection();
		connection.setClientID("CID1");
		final Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
		Topic topic = session.createTopic("topic1");
		TopicSubscriber subscriber = session.createDurableSubscriber(topic, "subscriber1");
		subscriber.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						System.out.println(textMessage.getText());
						session.commit();
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		connection.start();
		// subscriber.close();
		// session.close();
		// connection.close();
	}
}
