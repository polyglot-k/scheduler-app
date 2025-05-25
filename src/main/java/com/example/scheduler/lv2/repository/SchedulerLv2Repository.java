package com.example.scheduler.lv2.repository;

import com.example.scheduler.lv2.model.Scheduler;
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
public class SchedulerLv2Repository {
    private final JdbcTemplate jdbcTemplate;

    public void save(Scheduler scheduler){
        String sql = "INSERT INTO scheduler (todo, writer, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                scheduler.getTodo(),
                scheduler.getWriter(),
                scheduler.getPassword(),
                scheduler.getCreatedAt(),
                scheduler.getUpdatedAt()
        );
    }
    public void delete(Scheduler scheduler){
        String sql = "DELETE FROM scheduler WHERE id = ?";
        jdbcTemplate.update(sql, scheduler.getId());
    }
    public void update(Scheduler scheduler) {
        String sql = "UPDATE scheduler SET todo = ?, writer = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                scheduler.getTodo(),
                scheduler.getWriter(),
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
        String sql = "SELECT * FROM scheduler WHERE id = ?";
        Scheduler foundScheduler = jdbcTemplate.queryForObject(sql, schedulerRowMapper(), id);
        return Optional.of(foundScheduler);
    }

    private RowMapper<Scheduler> schedulerRowMapper() {
        return (rs, rowNum) -> new Scheduler(
                rs.getLong("id"),
                rs.getString("todo"),
                rs.getString("writer"),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime().toLocalDate(),
                rs.getTimestamp("updated_at").toLocalDateTime().toLocalDate()
        );
    }
}
