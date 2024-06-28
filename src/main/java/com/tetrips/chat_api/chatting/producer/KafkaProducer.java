package com.tetrips.chat_api.chatting.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class KafkaProducer {

  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;

  public void sendMessage(String topic, Object message) {
    kafkaTemplate.send(topic, message);
  }
}