package com.Stomp.Chat;

import com.Stomp.Chat.Repository.chatRoom.ChatRoomRepository;
import com.Stomp.Chat.Repository.chatRoom.ChatRoomRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<ChatRoom> findAllRoom() {
        List<ChatRoom> result = chatRoomRepository.findAll();
        Collections.reverse(result);
        return result;
    }

    //채팅방 불러오기
    public ChatRoom findById(String roomId) {
        return chatRooms.get(roomId);
    }

    //채팅방 생성
    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRoomRepository.save(chatRoom);
        chatRooms.put(chatRoom.getRoomId(), chatRoom);

        return chatRoom;
    }
}
