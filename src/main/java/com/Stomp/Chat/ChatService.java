package com.Stomp.Chat;

import com.Stomp.Chat.Repository.chatRoom.ChatRoomRepository;
import com.Stomp.Chat.Repository.chatRoom.ChatRoomRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {
    private Map<String, ChatRoom> chatRooms;
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatService(ChatRoomRepositoryImpl chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @PostConstruct
    private void init() {
        chatRooms = new HashMap<String, ChatRoom>();
    }

    //채팅방 전체 불러오기
    public List<ChatRoom> findAllRoom(String id) {
        try {
            List<ChatRoom> result = chatRoomRepository.findAll(id);
            Collections.reverse(result);
            return result;
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    //채팅방 불러오기
    public ChatRoom findById(String id, String roomId) {
        return chatRooms.get(roomId);
    }

    //채팅방 생성
    public ChatRoom createRoom(String id, String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRoomRepository.saveChatRoom(chatRoom);
        chatRoomRepository.saveChatUser(chatRoom.getRoomId(), id);
        chatRooms.put(chatRoom.getRoomId(), chatRoom);

        return chatRoom;
    }
}
