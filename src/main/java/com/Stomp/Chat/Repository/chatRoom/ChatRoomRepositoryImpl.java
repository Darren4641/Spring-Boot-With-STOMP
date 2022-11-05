package com.Stomp.Chat.Repository.chatRoom;

import com.Stomp.Chat.ChatRoom;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveChatRoom(ChatRoom chatRoom) {
        
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `carrotsql`.`chatRoom` (`roomId`, `roomName`) " +
                    "VALUES (?,?)");
            preparedStatement.setString(1, chatRoom.getRoomId());
            preparedStatement.setString(2, chatRoom.getRoomName());

            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);

    }

    @Override
    public void saveChatUser(String roomId, String id) {
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `chatRoomUser` values(?,?,1)");
            preparedStatement.setString(1, roomId);
            preparedStatement.setString(2, id);

            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
    }

    @Override
    public void deleteByRoomId(String roomId) {
        jdbcTemplate.update("DELETE FROM `carrotsql`.`chatRoom` WHERE ?", roomId);
    }

    @Override
    public List<ChatRoom> findAll(String id) {
        try {
            return jdbcTemplate.query(
                    "select chatroom.roomId, chatroom.roomName, chatuser.isRead " +
                            "from chatroom " +
                            "join chatroomuser as chatuser " +
                            "on chatroom.roomId = chatuser.roomId " +
                            "where chatuser.id = ? ",
                    new Object[]{id},
                    (rs, rowNum) ->
                            ChatRoom.builder()
                                    .roomId(rs.getString("roomId"))
                                    .roomName(rs.getString("roomName"))
                                    .isRead(rs.getInt("isRead")).build()
            );
        }catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Optional<String> findById(String id, String roomId) {
        try {
            return jdbcTemplate.queryForObject(
                    "select id " +
                            "from chatroomuser " +
                            "where id = '?' and roomId = ? limit 1 ",
                    new Object[]{id, roomId},
                    (rs, rowNum) ->
                            Optional.of(
                                    rs.getString("id")
                            )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ChatRoom> findByRoomId(String id, String roomId) {
        try {
            return jdbcTemplate.queryForObject(
                    "select chatroom.roomId, chatroom.roomName, chatuser.isRead " +
                            "from chatroom " +
                            "join chatroomuser as chatuser " +
                            "on chatroom.roomId = chatuser.roomId " +
                            "where chatuser.id = ? and chatroom.roomId = ? ",
                    new Object[]{id, roomId},
                    (rs, rowNum) ->
                            Optional.of(
                                    ChatRoom.builder()
                                            .roomId(rs.getString("roomId"))
                                            .roomName(rs.getString("roomName"))
                                            .isRead(rs.getInt("isRead")).build()
                            )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
