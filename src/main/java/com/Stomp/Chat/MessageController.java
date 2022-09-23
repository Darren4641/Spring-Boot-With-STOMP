package com.Stomp.Chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;

    //Config 파일에서 setApplicationDestinationPrefixes를 "/app"으로 해주었기때문에 실제
    //경로는 "/app/chat/message"이다.
    @MessageMapping("/chat/message")
    public void enter(ChatMessage message) {
        if(ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(), message);
    }
}
