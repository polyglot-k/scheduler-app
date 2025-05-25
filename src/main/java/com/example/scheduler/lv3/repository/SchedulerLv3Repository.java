package com.example.scheduler.lv3.repository;

import com.example.scheduler.lv3.model.Scheduler;
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
        String sql = "UPDATE scheduler.scheduler2 SET todo = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                scheduler.getTodo(),
                scheduler.getId()
        );
    }
    public List<Scheduler> findByCondition(String writer, LocalDate updatedFrom, LocalDate updatedTo) {
        StringBuilder sql = new StringBuilder("SELECT * FROM scheduler WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (writer != null) {
            sql.append(" AND writer = ?");
            params.add(writer);
        }

        if (updatedFrom != null && updatedTo != null) {
            sql.append(" AND updated_at BETWEEN ? AND ?");
            params.add(updatedFrom);
            params.add(updatedTo);
        } else if (updatedFrom != null) {
            sql.append(" AND updated_at >= ?");
            params.add(updatedFrom);
        } else if (updatedTo != null) {
            sql.append(" AND updated_at <= ?");
            params.add(updatedTo);
        }

        return jdbcTemplate.query(sql.toString(), schedulerRowMapper(), params.toArray());
    }

    public Optional<Scheduler> findById(Long id) {
        String sql = "SELECT * FROM scheduler.scheduler2 WHERE id = ?";
        Scheduler foundScheduler = jdbcTemplate.queryForObject(sql, schedulerRowMapper(), id);
        return Optional.of(foundScheduler);
    }

    private RowMapper<Scheduler> schedulerRowMapper() {
        return (rs, rowNum) -> new Scheduler(
                rs.getLong("id"),
                rs.getString("todo"),
                rs.getTimestamp("created_at").toLocalDateTime().toLocalDate(),
                rs.getTimestamp("updated_at").toLocalDateTime().toLocalDate(),
                null
        );
    }
}
