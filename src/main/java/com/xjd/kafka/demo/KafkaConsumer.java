package com.xjd.kafka.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

/**
 * 0.8.0
 * @author elvis.xu
 * @since 2015-10-01 23:13
 */
public class KafkaConsumer extends Thread {
	private final ConsumerConnector consumer = Consumer.createJavaConsumerConnector(createConsumerConfig());
	private final String topic;

	public KafkaConsumer(String topic) {
		this.topic = topic;
	}

	private static ConsumerConfig createConsumerConfig() {
		Properties props = new Properties();
		props.put("zookeeper.connect", "103.224.81.184:2181,103.224.81.184:2182");
//		props.put("zookeeper.connect", "103.224.81.184:2181");
		props.put("group.id", "group4");
		props.put("zookeeper.session.timeout.ms", "5000");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");
//		props.put("auto.offset.reset", "smallest");
//		props.put("fetch.message.max.bytes", "256");
		return new ConsumerConfig(props);
	}

	@Override
	public void run() {
		HashMap topicHashMap = new HashMap();
		topicHashMap.put(this.topic, new Integer(1));
		Map consumerMap = this.consumer.createMessageStreams(topicHashMap);
		KafkaStream kafkaStream = (KafkaStream) ((List) consumerMap.get(this.topic)).get(0);
		ConsumerIterator it = kafkaStream.iterator();
		while (it.hasNext()) {
			MessageAndMetadata messageAndMetadata = it.next();
			System.out.println(messageAndMetadata.partition() + "-" + messageAndMetadata.offset() + "-"
					+ messageAndMetadata.key() + "=" + new String(((byte[]) messageAndMetadata.message())));
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		KafkaConsumer consumer = new KafkaConsumer("test3");
		consumer.start();
	}
}
