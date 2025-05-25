package com.example.scheduler.lv5.repository;

import com.example.scheduler.lv5.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserLv5Repository {
    private final JdbcTemplate jdbcTemplate;
    public void save(User user){
        String sql = "INSERT INTO scheduler.users(email, name, password, created_at, updated_at) values (?,?,?,?,?)";
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getName(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
    public void update(User user) {
        String sql = "UPDATE scheduler.users SET name = ?, updated_at=?WHERE id = ?";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getUpdatedAt(),
                user.getId()
        );
    }
    public void delete(User user){
        String sql = "DELETE FROM scheduler.users WHERE id = ?";
        jdbcTemplate.update(sql, user.getId());
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM scheduler.users WHERE email = ?";
        User user = jdbcTemplate.queryForObject(sql, userRowMapper(), email);
        return Optional.of(user);
    }
    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime().toLocalDate(),
                rs.getTimestamp("updated_at").toLocalDateTime().toLocalDate()
        );
    }
}
