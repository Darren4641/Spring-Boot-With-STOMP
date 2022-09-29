package com.Stomp.Chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    public static final int SHOW_COUNT = 10;

    private final MessageService messageService;
    private final SimpMessageSendingOperations sendingOperations;

    //Config 파일에서 setApplicationDestinationPrefixes를 "/app"으로 해주었기때문에 실제
    //경로는 "/app/chat/message"이다.
    @MessageMapping("/chat/message")
    public void enter(ChatMessage message) {
        if(ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(), message);
        messageService.sendMessage(message);
    }

    @GetMapping("/chatlog/{page}")
    public List<ChatMessage> getChatLog(@PathVariable(name = "page") int pageNum) {
        //무한 스크롤
        int limit = getLimitCnt(pageNum);
        int offset = limit - 10;
        return messageService.setChatLog(limit, offset, pageNum);
    }

    private int getLimitCnt(int pageNum) {
        int limit = SHOW_COUNT;
        for(int i = 0; i <= pageNum; i++) {
            if(i != 0)
                limit += SHOW_COUNT;
        }
        return limit;
    }
}
