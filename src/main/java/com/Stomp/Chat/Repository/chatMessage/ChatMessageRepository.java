package com.Stomp.Chat.Repository.chatMessage;

import com.Stomp.Chat.ChatMessage;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository {
    void saveChatRoom(ChatMessage chatMessage);
    void deleteByRoomIdAndSenderAndMessage(ChatMessage chatMessage);
    Optional<ChatMessage> findByRoomIdAndSender(ChatMessage chatMessage);
    List<ChatMessage> findPageCount(String roomId, int limit, int offset);

}
