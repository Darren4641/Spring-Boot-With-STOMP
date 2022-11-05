package com.Stomp.Chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    private MessageType type;
    private String roomId;
    private String sender;
    private String content;
    private Timestamp createDate;

    @AllArgsConstructor
    @Getter
    public enum MessageType {
        ENTER("ENTER"),
        TALK("TALK"),
        LEAVE("LEAVE");

        private String value;
        public static MessageType from(String s) {
            return MessageType.valueOf(s.toUpperCase());
        }
    }
}
