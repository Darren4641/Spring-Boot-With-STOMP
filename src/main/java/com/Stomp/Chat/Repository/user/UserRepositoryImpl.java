package com.Stomp.Chat.Repository.user;

import com.Stomp.Chat.User;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `usertemp` values(?,?,?)");
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getTitle());

            return preparedStatement;
        };
        jdbcTemplate.update(preparedStatementCreator);
    }

    @Override
    public Optional<User> findById(User user) {
        try {
            return jdbcTemplate.queryForObject(
                    "select `id`, `password` from usertemp where id = ? ",
                    new Object[]{user.getId()},
                    (rs, rowNum) ->
                            Optional.of(
                                    User.builder()
                                    .id(rs.getString("id"))
                                    .password(rs.getString("password")).build()
                            )
            );
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return jdbcTemplate.query(
                    "select `id`, `password`, `title` from usertemp ",
                    (rs, rowNum) ->
                            User.builder()
                                    .id(rs.getString("id"))
                                    .password(rs.getString("password"))
                                    .title(rs.getString("title")).build()
            );
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
