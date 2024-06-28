package com.tetrips.chat_api.config;

import com.tetrips.chat_api.websocket.ChatWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebFluxConfigurer {

  private final ChatWebSocketHandler chatWebSocketHandler;

  public WebSocketConfig(ChatWebSocketHandler chatWebSocketHandler) {
    this.chatWebSocketHandler = chatWebSocketHandler;
  }

  @Bean
  public WebSocketHandlerAdapter handlerAdapter() {
    return new WebSocketHandlerAdapter();
  }

  @Bean
  public SimpleUrlHandlerMapping urlHandlerMapping() {
    Map<String, Object> map = new HashMap<>();
    map.put("/ws/chat", chatWebSocketHandler);

    SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
    mapping.setUrlMap(map);
    mapping.setOrder(-1);  // Specify order for handler mapping
    return mapping;
  }
}