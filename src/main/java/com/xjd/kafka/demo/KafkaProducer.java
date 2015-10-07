package com.xjd.kafka.demo;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * 0.8.0
 * @author elvis.xu
 * @since 2015-10-02 01:02
 */
public class KafkaProducer extends Thread {
	private final Producer<Integer, String> producer;
	private final String topic;
	private final Properties pros = new Properties();

	public KafkaProducer(String topic) {
		this.pros.put("serializer.class", "kafka.serializer.StringEncoder");
		this.pros.put("metadata.broker.list", "103.224.81.184:9092,103.224.81.184:9093");
		this.producer = new Producer<Integer, String>(new ProducerConfig(pros));
		this.topic = topic;
	}

	@Override
	public void run() {
		int msgNo = 1;
		while (true) {
			String msgStr = "Message_" + msgNo + ":xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
			this.producer.send(new KeyedMessage<Integer, String>(this.topic, msgStr));
			if (msgNo % 10 == 0) {
				break;
			}
			++msgNo;
		}
	}

	public static void main(String[] args) {
		KafkaProducer producer = new KafkaProducer("test3");
		producer.start();
	}
}
