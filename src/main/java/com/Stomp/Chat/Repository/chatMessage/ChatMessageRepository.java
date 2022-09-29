package com.Stomp.Chat.Repository.chatMessage;

import com.Stomp.Chat.ChatMessage;

import java.util.List;

public interface ChatMessageRepository {
    void save(ChatMessage chatMessage);
    void deleteByRoomIdAndSenderAndMessage(ChatMessage chatMessage);
    List<ChatMessage> findPageCount(int limit, int offset);

}
