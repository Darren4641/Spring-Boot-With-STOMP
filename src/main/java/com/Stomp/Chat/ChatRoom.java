package com.Stomp.Chat;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatRoom {
    private String roomId;
    private String roomName;
    private int isRead;

    public ChatRoom() {

    }
    public static ChatRoom create(String name) {
        ChatRoom room = new ChatRoom();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = name;
        return room;
    }

}
