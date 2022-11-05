package com.Stomp.Chat.Repository.user;

import com.Stomp.Chat.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(User user);
    List<User> findAll();

}
