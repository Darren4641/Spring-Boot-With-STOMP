package com.Stomp.Chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/")
    public String index() {
        return "index";
    }

    //모든 채팅방
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room(String id) {
        System.out.println("id : " +  id);
        return chatService.findAllRoom(id);
    }

    //채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestBody Map<String, Object> params) {
        return chatService.createRoom(params.get("id").toString(), params.get("name").toString());
    }

    //채팅방 입장
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(@PathVariable String roomId) {
        return "/chat/roomdetail";
    }

    @GetMapping("/chatlog/{roomId}/{page}")
    public List<ChatMessage> getChatLog(@PathVariable(name = "roomId") String roomId, @PathVariable(name = "page") int pageNum) {
        //무한 스크롤
        int limit = getLimitCnt(pageNum);
        int offset = limit - 10;
        //local Storage에서 데이터 가져오기
        return messageService.setChatLog(roomId, limit, offset);
    }

    //채팅방 검색
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String id, @PathVariable String roomId) {
        return chatService.findById(id, roomId);
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
