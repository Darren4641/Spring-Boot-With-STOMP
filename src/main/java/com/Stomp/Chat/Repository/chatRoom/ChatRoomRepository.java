package com.Stomp.Chat.Repository.chatRoom;

import com.Stomp.Chat.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {
    String save(ChatRoom chatRoom);
    void deleteByRoomId(String roomId);
    List<ChatRoom> findAll();
    Optional<ChatRoom> findByRoomId(String roomId);
}
