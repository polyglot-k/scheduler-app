package com.example.scheduler;

import com.example.scheduler.lv3.service.UserLv3Service;
import com.example.scheduler.lv3.dto.UserRequestDto;
import com.example.scheduler.lv3.model.Scheduler;
import com.example.scheduler.lv3.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SchedulerApplicationTests {
    @Autowired
    private UserLv3Service urepository;
    @Test
    void contextLoads() {

        urepository.create(new UserRequestDto("asd","asd","asd"));
    }

}
