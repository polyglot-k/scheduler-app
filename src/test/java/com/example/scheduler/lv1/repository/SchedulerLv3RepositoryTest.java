package com.example.scheduler.lv1.repository;

import com.example.scheduler.lv1.model.Scheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/db/schema.sql")
@Sql(value = "/db/test/init_data.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SchedulerLv3RepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SchedulerRepository schedulerRepository;

    @BeforeEach
    void setUp() {
        schedulerRepository = new SchedulerRepository(jdbcTemplate);
    }

    @Test
    void 새로운_할_일을_저장할_수_있다() {
        // Given
        LocalDate now = LocalDate.of(2025,5,6);
        Scheduler scheduler = new Scheduler(4L, "장보기", "강태공", "1234", now, now);

        // When
        schedulerRepository.save(scheduler);
;
        // Then
        List<Scheduler> results = schedulerRepository.findByCondition(null,null,null);
        System.out.println(results.toString());
        System.out.println(results.size());
        assertThat(results).hasSize(4);
        for(var result : results){
            System.out.println(result.getId());
        }
        assertThat(results.get(0).getTodo()).isEqualTo("장보기");
        assertThat(results.get(0).getWriter()).isEqualTo("강태공");
    }

    @Test
    void ID로_특정_할_일을_조회할_수_있다() {
        // Given
        Long targetId = 1L;

        // When
        Optional<Scheduler> foundScheduler = schedulerRepository.findById(targetId);

        // Then
        assertThat(foundScheduler).isPresent();
        assertThat(foundScheduler.get().getTodo()).isEqualTo("청소");
        assertThat(foundScheduler.get().getWriter()).isEqualTo("고길동");
    }

    @Test
    void 존재하지_않는_ID로_조회하면_빈_Optional을_반환한다() {
        // Given
        Long nonExistingId = 999L;

        // When
        Optional<Scheduler> foundScheduler = schedulerRepository.findById(nonExistingId);

        // Then
        assertThat(foundScheduler).isEmpty();
    }

    @Test
    void 작성자로_할_일_목록을_조회할_수_있다() {
        // Given
        String writer = "고길동";

        // When
        List<Scheduler> schedulers = schedulerRepository.findByCondition(writer, null, null);

        // Then
        assertThat(schedulers).hasSize(1);
        assertThat(schedulers.get(0).getWriter()).isEqualTo("고길동");
        assertThat(schedulers.get(0).getTodo()).isEqualTo("청소");
    }

    @Test
    void 업데이트_날짜_범위로_할_일_목록을_조회할_수_있다() {
        // Given
        LocalDate startDate = LocalDate.of(2025, 5, 2);
        LocalDate endDate = LocalDate.of(2025, 5, 4);

        // When
        List<Scheduler> schedulers = schedulerRepository.findByCondition(null, startDate, endDate);

        // Then
        assertThat(schedulers).hasSize(2);
        assertThat(schedulers).extracting(Scheduler::getUpdatedAt).allMatch(date ->
                !date.isBefore(startDate) && !date.isAfter(endDate)
        );
    }

    @Test
    void 작성자와_업데이트_날짜_범위로_할_일_목록을_조회할_수_있다() {
        // Given
        String writer = "강감찬";
        LocalDate startDate = LocalDate.of(2025, 5, 3);
        LocalDate endDate = LocalDate.of(2025, 5, 5);

        // When
        List<Scheduler> schedulers = schedulerRepository.findByCondition(writer, startDate, endDate);

        // Then
        assertThat(schedulers).hasSize(1);
        assertThat(schedulers.get(0).getWriter()).isEqualTo("강감찬");
        assertThat(schedulers.get(0).getTodo()).isEqualTo("빨래");
        assertThat(schedulers.get(0).getUpdatedAt()).isBetween(startDate, endDate);
    }

    @Test
    void 업데이트_시작_날짜_이후의_할_일_목록을_조회할_수_있다() {
        // Given
        LocalDate startDate = LocalDate.of(2025, 5, 2);

        // When
        List<Scheduler> schedulers = schedulerRepository.findByCondition(null, startDate, null);

        // Then
        assertThat(schedulers).hasSize(3);
        assertThat(schedulers).extracting(Scheduler::getUpdatedAt).allMatch(date -> !date.isBefore(startDate));
    }

    @Test
    void 업데이트_종료_날짜_이전의_할_일_목록을_조회할_수_있다() {
        // Given
        LocalDate endDate = LocalDate.of(2025, 5, 3);

        // When
        List<Scheduler> schedulers = schedulerRepository.findByCondition(null, null, endDate);

        // Then
        assertThat(schedulers).hasSize(1);
        assertThat(schedulers).extracting(Scheduler::getUpdatedAt).allMatch(date -> !date.isAfter(endDate));
    }
    @Test
    void 수정일_기준_내림차순으로_모든_할_일을_조회할_수_있다() {
        // Given
        // 데이터는 @Sql 스크립트에 의해 설정됨

        // When
        List<Scheduler> schedulers = schedulerRepository.findByCondition(null, null, null);

        // Then
        assertThat(schedulers).hasSize(3);
        List<LocalDate> updatedAts = schedulers.stream()
                .map(Scheduler::getUpdatedAt)
                .toList();
        List<LocalDate> sortedUpdatedAtsDesc = updatedAts.stream()
                .sorted(Comparator.reverseOrder())
                .toList();
        // 추가적으로 내용도 확인해볼 수 있습니다.
        assertThat(schedulers.get(0).getId()).isEqualTo(3);
        assertThat(schedulers.get(1).getId()).isEqualTo(2);
        assertThat(schedulers.get(2).getId()).isEqualTo(1);
    }
}