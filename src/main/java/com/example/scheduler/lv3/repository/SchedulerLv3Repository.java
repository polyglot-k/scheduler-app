package com.example.scheduler.lv3.repository;

import com.example.scheduler.lv3.model.Scheduler;
import com.example.scheduler.lv3.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SchedulerLv3Repository {
    private final JdbcTemplate jdbcTemplate;

    public void save(Scheduler scheduler){
        String sql = "INSERT INTO scheduler.scheduler2 (todo, created_at, updated_at, u_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                scheduler.getTodo(),
                scheduler.getCreatedAt(),
                scheduler.getUpdatedAt(),
                scheduler.getUser().getId()
        );
    }
    public void delete(Scheduler scheduler){
        String sql = "DELETE FROM scheduler.scheduler2 WHERE id = ?";
        jdbcTemplate.update(sql, scheduler.getId());
    }
    public void update(Scheduler scheduler) {
        String sql = "UPDATE scheduler.scheduler2 SET todo = ?,updated_at=? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                scheduler.getTodo(),
                scheduler.getUpdatedAt(),
                scheduler.getId()
        );
    }
    public List<Scheduler> findByCondition(Long userId, LocalDate updatedAt) {
        StringBuilder sql = new StringBuilder("SELECT s.*, u.* FROM scheduler.scheduler2 s inner join scheduler.users u on s.u_id = u.id WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (userId != null) {
            sql.append(" AND u.id = ?");
            params.add(userId);
        }

        if (updatedAt != null) {
            sql.append(" AND s.updated_at = ?");
            params.add(updatedAt);
        }
        sql.append(" order by updated_at desc");

        return jdbcTemplate.query(sql.toString(), schedulerRowMapper(), params.toArray());
    }

    public Optional<Scheduler> findById(Long id) {
        String sql = "SELECT s.*, u.* FROM scheduler.scheduler2 s inner join scheduler.users u on s.u_id = u.id WHERE u.id = ?";
        Scheduler foundScheduler = jdbcTemplate.queryForObject(sql, schedulerRowMapper(), id);
        return Optional.of(foundScheduler);
    }

    private RowMapper<Scheduler> schedulerRowMapper() {
        return (rs, rowNum) -> {
            User user = new User(
                    rs.getLong("u.id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getTimestamp("u.created_at").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("u.updated_at").toLocalDateTime().toLocalDate()
            );
            return new Scheduler(
                    rs.getLong("s.id"),
                    rs.getString("todo"),
                    rs.getTimestamp("s.created_at").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("s.updated_at").toLocalDateTime().toLocalDate(),
                    user
            );
        };
    }
}
