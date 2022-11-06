package com.Stomp.Chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MessageController {
    public static final int SHOW_COUNT = 10;

    private final MessageService messageService;
    private final SimpMessageSendingOperations sendingOperations;

    //Config 파일에서 setApplicationDestinationPrefixes를 "/app"으로 해주었기때문에 실제
    //경로는 "/app/chat/message"이다.
    @MessageMapping("/chat/enter")
    public ChatMessage enter(ChatMessage chatMessage) {
//        String id = params.get("id").toString();
//        String roomId = params.get("roomId").toString();
//        String message = "";
//        if(ChatMessage.MessageType.ENTER.equals(params.get("type").toString())) {
//            if(messageService.validateChatRoom(id, roomId))
//                message = id + "님이 입장하셨습니다.";
//        }
//
//        if(params.get("message").toString() != null) {
//            message = params.get("message").toString();
//        }
//        sendingOperations.convertAndSend("/topic/chat/room/"+roomId, message);
        sendingOperations.convertAndSend("/topic/chat/room/"+ chatMessage.getRoomId(), chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat/message")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        sendingOperations.convertAndSend("/topic/chat/room/"+ chatMessage.getRoomId(), chatMessage);
        return chatMessage;
    }

    @GetMapping("/chatlog/{roomId}/{page}")
    public List<ChatMessage> getChatLog(@PathVariable(name = "roomId") String roomId, @PathVariable(name = "page") int pageNum) {
        //무한 스크롤
        int limit = getLimitCnt(pageNum);
        int offset = limit - 10;
        return messageService.setChatLog(roomId, limit, offset);
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
