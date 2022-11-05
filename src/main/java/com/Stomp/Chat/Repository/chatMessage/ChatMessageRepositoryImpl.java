package com.Stomp.Chat.Repository.chatMessage;

import com.Stomp.Chat.ChatMessage;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ChatMessageRepositoryImpl implements  ChatMessageRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveChatRoom(ChatMessage chatMessage) {
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `carrotsql`.`chatMessage`(`type`,`roomId`,`sender`,`message`,`createDate`)" +
                    "VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, chatMessage.getType().getValue());
            preparedStatement.setString(2, chatMessage.getRoomId());
            preparedStatement.setString(3, chatMessage.getSender());
            preparedStatement.setString(4, chatMessage.getContent());
            preparedStatement.setTimestamp(5, chatMessage.getCreateDate());

            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
    }

    @Override
    public void deleteByRoomIdAndSenderAndMessage(ChatMessage chatMessage) {
        jdbcTemplate.update("DELETE FROM `carrotsql`.`chatMessage` WHERE `roomId` = ? AND `sender` = ? AND `message` = ?", chatMessage.getRoomId(), chatMessage.getSender(), chatMessage.getContent());
    }

    @Override
    public Optional<ChatMessage> findByRoomIdAndSender(ChatMessage chatMessage) {
        jdbcTemplate.query(
                "SELECT `chatmessage`.`type`," +
                        "    `chatmessage`.`roomId`," +
                        "    `chatmessage`.`sender`," +
                        "FROM `carrotsql`.`chatmessage` WHERE roomId = ? AND sender = ?",
                new Object[]{chatMessage.getRoomId(), chatMessage.getSender()},
                        (rs, rowNum) ->
                Optional.of(new ChatMessage(
                        ChatMessage.MessageType.from(rs.getString("type")),
                        rs.getString("roomId"),
                        rs.getString("sender"),
                        null,
                        null
                ))
        );
        return Optional.empty();
    }

    @Override
    public List<ChatMessage> findPageCount(String roomId, int limit, int offset) {
        List<ChatMessage> results = jdbcTemplate.query(
                "SELECT `chatMessage`.`type`," +
                        "`chatMessage`.`roomId`," +
                        "`chatMessage`.`sender`," +
                        "`chatMessage`.`message`," +
                        "`chatMessage`.`createDate`" +
                        "FROM `carrotsql`.`chatMessage` WHERE `chatMessage`.`roomId` = ? ORDER BY `createDate` DESC LIMIT ? OFFSET ?",
                new RowMapper<ChatMessage>() {
                    @Override
                    public ChatMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ChatMessage chatMessage = new ChatMessage(
                                ChatMessage.MessageType.from(rs.getString("type")),
                                rs.getString("roomId"),
                                rs.getString("sender"),
                                rs.getString("message"),
                                rs.getTimestamp("createDate")
                        );
                        return chatMessage;
                    }
        }, roomId, limit, offset);
        return results.isEmpty() ? null : results;
    }


}
