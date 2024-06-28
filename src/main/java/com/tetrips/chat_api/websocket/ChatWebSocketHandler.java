package com.tetrips.chat_api.websocket;

import com.tetrips.chat_api.chatting.message.MessageService;
import com.tetrips.chat_api.chatting.message.ChatMessage;
import com.tetrips.chat_api.chatting.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler implements WebSocketHandler {

  private final KafkaProducer kafkaProducer;
  private final MessageService messageService;
  private final ObjectMapper objectMapper;

  @Override
  public Mono<Void> handle(WebSocketSession session) {
    Flux<WebSocketMessage> messageFlux = session.receive()
            .map(WebSocketMessage::getPayloadAsText)
            .flatMap(message -> {
              try {
                ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);
                chatMessage.setId(UUID.randomUUID().toString()); // Set unique ID
                chatMessage.setTimestamp(System.currentTimeMillis()); // Set timestamp
                return messageService.saveMessage(chatMessage)
                        .doOnNext(savedMessage -> kafkaProducer.sendMessage("chat-topic", savedMessage))
                        .flatMap(savedMessage -> {
                          try {
                            return Mono.just(session.textMessage(objectMapper.writeValueAsString(savedMessage)));
                          } catch (Exception e) {
                            log.error("Error serializing message", e);
                            return Mono.empty();
                          }
                        });
              } catch (Exception e) {
                log.error("Error processing message", e);
                return Mono.empty();
              }
            });

    return session.send(messageFlux);
  }
}