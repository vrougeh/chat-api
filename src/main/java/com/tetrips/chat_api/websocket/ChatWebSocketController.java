package com.tetrips.chat_api.websocket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatWebSocketController {

  @GetMapping("/api/ws/info")
  public String webSocketInfo() {
    return "WebSocket endpoint is available at /ws/chat";
  }

}