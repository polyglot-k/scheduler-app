package com.example.scheduler;

import com.example.scheduler.lv4.dto.Pageable;
import com.example.scheduler.lv4.model.Scheduler;
import com.example.scheduler.lv4.repository.SchedulerLv4Repository;
import com.example.scheduler.lv4.repository.UserLv4Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class SchedulerApplication implements CommandLineRunner {
    private final UserLv4Repository userLv3Repository;
    private final SchedulerLv4Repository schedulerRepository;

    public static void main(String[] args) {
        SpringApplication.run(SchedulerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        userLv3Repository.save(User.from(
//                "name",
//                "email@email.com",
//                "1234"
//        ));
//        User user = userLv3Repository.findByEmail("email@email.com")
//                        .orElseThrow();
//        schedulerRepository.save(Scheduler.from(
//                "todo exmample",
//                user
//        ));
        Pageable<Scheduler> scheduler= schedulerRepository.findByCondition(1L, null, 4, 1);

        log.info(scheduler.toString());
    }
}
