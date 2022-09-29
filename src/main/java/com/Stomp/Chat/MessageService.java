package com.Stomp.Chat;

import com.Stomp.Chat.Repository.chatMessage.ChatMessageRepository;
import com.Stomp.Chat.Repository.chatMessage.ChatMessageRepositoryImpl;
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
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    public MessageService(ChatMessageRepositoryImpl chatRoomRepository) {
        this.chatMessageRepository = chatRoomRepository;
    }

    public void sendMessage(ChatMessage chatMessage) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        chatMessage.setCreateDate(Timestamp.valueOf(sdf.format(timestamp)));
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> setChatLog(int limit, int offset, int pageNum) {
        return chatMessageRepository.findPageCount(limit, offset);
    }

}
