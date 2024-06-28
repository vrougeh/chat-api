package com.tetrips.chat_api.chatting.message;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "messages")
public class ChatMessage {

  @Id
  private String id;
  private String nickname;
  private String message;
  private long timestamp;
  private String userId;
  private String prId;

  @Builder(builderMethodName = "builder")
  public ChatMessage(String id, String nickname, String message, long timestamp, String userId, String prId) {
    this.id = id;
    this.nickname = nickname;
    this.message = message;
    this.timestamp = timestamp;
    this.userId = userId;
    this.prId = prId;
  }
}
