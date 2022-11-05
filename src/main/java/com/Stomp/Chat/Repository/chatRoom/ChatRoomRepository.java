package com.Stomp.Chat.Repository.chatRoom;

import com.Stomp.Chat.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository {
    void saveChatRoom(ChatRoom chatRoom);
    void saveChatUser(String roomId, String id);
    void deleteByRoomId(String roomId);
    List<ChatRoom> findAll(String id);
    Optional<ChatRoom> findByRoomId(String id, String roomId);
    Optional<String> findById(String id, String roomId);

}
