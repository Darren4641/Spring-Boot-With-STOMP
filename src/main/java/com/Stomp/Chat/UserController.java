package com.Stomp.Chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/main")
    public String main() {
        return "mainView";
    }

    @GetMapping("/signup")
    public String signup() { return "signup"; }

    @PostMapping("/signup")
    public String signup(User user) {
        userService.save(user);
        return "mainView";
    }

    @GetMapping("/userinfo")
    public String info(Model model) {
        return "userInfo";
    }
}
