package com.example.scheduler.lv6.repository;

import com.example.scheduler.lv6.dto.common.Pageable;
import com.example.scheduler.lv6.model.Scheduler;
import com.example.scheduler.lv6.model.User;
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
public class SchedulerLv6Repository {
    private final JdbcTemplate jdbcTemplate;

    private static class QueryConditions {
        public StringBuilder whereClause;
        public List<Object> params;

        public QueryConditions(StringBuilder whereClause, List<Object> params) {
            this.whereClause = whereClause;
            this.params = params;
        }
    }

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
        String sql = "UPDATE scheduler.scheduler2 SET todo = ?, updated_at=? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                scheduler.getTodo(),
                scheduler.getUpdatedAt(),
                scheduler.getId()
        );
    }
    public Pageable<Scheduler> findByCondition(Long userId, LocalDate updatedAt, int page, int size) {
        QueryConditions conditions = buildQueryConditions(userId, updatedAt);

        long totalElements = getTotalCount(conditions);

        List<Scheduler> content = getPaginatedContent(conditions, page, size);

        return new Pageable<>(content, page, size, totalElements);
    }
    public Optional<Scheduler> findById(Long id) {
        String sql = "SELECT s.*, u.* FROM scheduler.scheduler2 s inner join scheduler.users u on s.u_id = u.id WHERE u.id = ?";
        Scheduler foundScheduler = jdbcTemplate.queryForObject(sql, schedulerRowMapper(), id);
        return Optional.of(foundScheduler);
    }
    private QueryConditions buildQueryConditions(Long userId, LocalDate updatedAt) {
        StringBuilder whereClause = new StringBuilder(" WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (userId != null) {
            whereClause.append(" AND u.id = ?");
            params.add(userId);
        }
        if (updatedAt != null) {
            whereClause.append(" AND s.updated_at = ?");
            params.add(updatedAt);
        }
        return new QueryConditions(whereClause, params);
    }

    private long getTotalCount(QueryConditions conditions) {
        String countSql = "SELECT COUNT(s.id) FROM scheduler.scheduler2 s inner join scheduler.users u on s.u_id = u.id" + conditions.whereClause.toString();
        return jdbcTemplate.queryForObject(countSql, Long.class, conditions.params.toArray());
    }

    private List<Scheduler> getPaginatedContent(QueryConditions conditions, int page, int size) {
        String contentSql = "SELECT s.*, u.* FROM scheduler.scheduler2 s inner join scheduler.users u on s.u_id = u.id"
                + conditions.whereClause.toString() +
                " order by s.updated_at desc limit ? offset ?";

        List<Object> contentParams = new ArrayList<>(conditions.params); // Copy params for content query
        contentParams.add(size);
        int offset = page * size;
        contentParams.add(offset);

        return jdbcTemplate.query(contentSql, schedulerRowMapper(), contentParams.toArray());
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
