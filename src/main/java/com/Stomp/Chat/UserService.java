package com.Stomp.Chat;

import com.Stomp.Chat.Repository.user.UserRepository;
import com.Stomp.Chat.Repository.user.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> login(User user) {
        Optional<User> userOptional = userRepository.findById(user);
        if(userOptional.isPresent()) {
            return userRepository.findAll();
        }
        return null;
    }
}
