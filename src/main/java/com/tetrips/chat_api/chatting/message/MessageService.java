package com.tetrips.chat_api.chatting.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class MessageService {
  @Autowired
  private ChatMessageRepository repository;

  public Mono<ChatMessage> saveMessage(ChatMessage message) {
    message.setTimestamp(Instant.now().toEpochMilli());  // Set the timestamp before saving
    return repository.save(message);
  }

  public Flux<ChatMessage> getAllMessages() {
    return repository.findAll();
  }
}
