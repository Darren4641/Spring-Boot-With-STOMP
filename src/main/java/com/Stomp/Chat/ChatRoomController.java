package com.Stomp.Chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    public static final int SHOW_COUNT = 10;
    private final ChatService chatService;
    private final MessageService messageService;

    //채팅 리스트
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    //모든 채팅방
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatService.findAllRoom();
    }

    //채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    //채팅방 입장
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(@PathVariable String roomId) {
        return "/chat/roomdetail";
    }

    @GetMapping("/chatlog/{page}")
    public List<ChatMessage> getChatLog(@PathVariable(name = "page") int pageNum) {
        //무한 스크롤
        int limit = getLimitCnt(pageNum);
        int offset = limit - 10;
        return messageService.setChatLog(limit, offset, pageNum);
    }

    //채팅방 검색
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatService.findById(roomId);
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
