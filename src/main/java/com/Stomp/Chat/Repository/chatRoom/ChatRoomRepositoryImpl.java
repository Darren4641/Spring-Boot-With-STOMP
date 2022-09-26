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
    public String save(ChatRoom chatRoom) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `carrotsql`.`chatRoom` (`roomId`, `roomName`) " +
                    "VALUES (?,?)", new String[]{"roomId"});
            preparedStatement.setString(1, chatRoom.getRoomId());
            preparedStatement.setString(2, chatRoom.getRoomName());

            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().toString();
    }

    @Override
    public void deleteByRoomId(String roomId) {
        jdbcTemplate.update("DELETE FROM `carrotsql`.`chatRoom` WHERE ?", roomId);
    }

    @Override
    public List<ChatRoom> findAll() {
        return jdbcTemplate.query(
                "SELECT `chatRoom`.`roomId`, `chatRoom`.`roomName` FROM `carrotsql`.`chatRoom`",
                (rs, rowNum) ->
                        new ChatRoom(
                                rs.getString("roomId"),
                                rs.getString("roomName")
                        )
        );
    }

    @Override
    public Optional<ChatRoom> findByRoomId(String roomId) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT `chatRoom`.`roomId`,`chatRoom`.`roomName` FROM `carrotsql`.`chatRoom` WHERE `roomId` = ?",
                    new Object[]{roomId},
                    (rs, rowNum) ->
                            Optional.of(new ChatRoom(
                                    rs.getString("roomId"),
                                    rs.getString("roomName")
                            ))
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
