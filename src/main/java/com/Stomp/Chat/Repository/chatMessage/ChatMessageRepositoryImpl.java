package com.Stomp.Chat.Repository.chatMessage;

import com.Stomp.Chat.ChatMessage;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class ChatMessageRepositoryImpl implements  ChatMessageRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String save(ChatMessage chatMessage) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `carrotsql`.`chatMessage`(`type`,`roomId`,`sender`,`message`,`createDate`)" +
                    "VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, chatMessage.getType().getValue());
            preparedStatement.setString(2, chatMessage.getRoomId());
            preparedStatement.setString(3, chatMessage.getSender());
            preparedStatement.setString(4, chatMessage.getMessage());
            preparedStatement.setTimestamp(5, chatMessage.getCreateDate());

            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().toString();
    }

    @Override
    public void deleteByRoomIdAndSenderAndMessage(ChatMessage chatMessage) {
        jdbcTemplate.update("DELETE FROM `carrotsql`.`chatMessage` WHERE `roomId` = ? AND `sender` = ? AND `message` = ?", chatMessage.getRoomId(), chatMessage.getSender(), chatMessage.getMessage());
    }

    @Override
    public List<ChatMessage> findPageCount(int limit, int offset) {
        List<ChatMessage> results = jdbcTemplate.query(
                "SELECT `chatMessage`.`type`," +
                        "`chatMessage`.`roomId`," +
                        "`chatMessage`.`sender`," +
                        "`chatMessage`.`message`" +
                        "FROM `carrotsql`.`chatMessage` ORDER BY `createDate` DESC LIMIT ? OFFSET ?",
                new RowMapper<ChatMessage>() {
                    @Override
                    public ChatMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ChatMessage chatMessage = new ChatMessage(
                                rs.getObject("type", ChatMessage.MessageType.class),
                                rs.getString("roomId"),
                                rs.getString("sender"),
                                rs.getString("message"),
                                rs.getTimestamp("createDate")
                        );
                        return chatMessage;
                    }
        }, limit, offset);
        return results.isEmpty() ? null : results;
    }


}
