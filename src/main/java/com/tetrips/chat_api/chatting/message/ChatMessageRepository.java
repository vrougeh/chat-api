package com.tetrips.chat_api.chatting.message;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ChatMessageRepository extends ReactiveMongoRepository<ChatMessage, String> {
}
