package com.Stomp.Chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/main")
    public String mainView() {
        return "/user/mainView";
    }

    @GetMapping("/signup")
    public String signup() { return "/user/signup"; }

    @GetMapping("/login")
    public String login() { return "/user/login"; }

    @PostMapping("/login")
    public String login(HttpSession session, Model model, User user) {
        List<User> userList = userService.login(user);
        session.setAttribute("myId", user.getId());
        model.addAttribute("userList", userList);

        return "/user/userInfo";
    }

    @PostMapping("/signup")
    public String signup(User user) {
        userService.save(user);
        return "/user/mainView";
    }

    @GetMapping("/userinfo")
    public String info(Model model) {
        return "/user/userInfo";
    }
}
