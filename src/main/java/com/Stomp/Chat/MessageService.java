package com.Stomp.Chat;

import com.Stomp.Chat.Repository.chatMessage.ChatMessageRepository;
import com.Stomp.Chat.Repository.chatMessage.ChatMessageRepositoryImpl;
import com.Stomp.Chat.Repository.chatRoom.ChatRoomRepository;
import com.Stomp.Chat.Repository.chatRoom.ChatRoomRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private ChatRoomRepository chatRoomRepository;
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    public MessageService(ChatRoomRepositoryImpl chatRoomRepository, ChatMessageRepositoryImpl chatMessageRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    public void sendMessage(ChatMessage chatMessage) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        chatMessage.setCreateDate(Timestamp.valueOf(sdf.format(timestamp)));
        chatMessageRepository.saveChatRoom(chatMessage);
    }

    public boolean validateChatRoom(String id, String roomId) {
        return chatRoomRepository.findByRoomId(id, roomId).isPresent();
    }

    public List<ChatMessage> setChatLog(String roomId, int limit, int offset) {
        return chatMessageRepository.findPageCount(roomId, limit, offset);
    }

}
