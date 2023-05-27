package com.github.kc596.simple;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaProducerExample {

  public static void main(String[] args) {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    // Create Kafka producer
    Producer<String, String> producer = new KafkaProducer<>(props);

    System.out.println("Starting to produce messages.");
    // Send messages to Kafka topic
    for (int i = 0; i < 10; i++) {
      String key = "key" + i;
      String value = "value" + i;
      System.out.println("Producing message: "+ value);

      ProducerRecord<String, String> record = new ProducerRecord<>("java_simple_topic", key, value);
      producer.send(record);
    }

    System.out.println("Produced 10 messages.");
    // Close the producer
    producer.close();
  }
}
