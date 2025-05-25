package com.example.scheduler.lv4.service;

import com.example.scheduler.lv4.dto.*;
import com.example.scheduler.lv4.model.Scheduler;
import com.example.scheduler.lv4.model.User;
import com.example.scheduler.lv4.repository.SchedulerLv4Repository;
import com.example.scheduler.lv4.repository.UserLv4Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerLv4Service {

    private final SchedulerLv4Repository repository;
    private final UserLv4Repository userLv4Repository;

    public Pageable<Scheduler> findByCondition(SchedulerSearchConditionDto searchConditionDto) {
        return repository.findByCondition(
                    searchConditionDto.getUserId(),
                    searchConditionDto.getUpdatedAt(),
                    searchConditionDto.getPage() - 1,
                    searchConditionDto.getSize()
                );
    }

    public SchedulerResponseDto findById(Long id) {
        Scheduler foundScheduler =  repository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));
        return SchedulerResponseDto.of(foundScheduler);
    }

    public void create(SchedulerRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        User user = userLv4Repository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("이메일 없다."));
        if(!user.isMatchPassword(password))
            throw new RuntimeException();
        Scheduler scheduler = Scheduler.from(requestDto.getTodo(), user);
        repository.save(scheduler);
    }
    public void update(Long id, SchedulerRequestDto requestDto) {
        Scheduler scheduler = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        User user = userLv4Repository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("이메일 없다."));
        if(!user.isMatchPassword(password))
            throw new RuntimeException();

        scheduler.updateTodo(requestDto.getTodo());
        repository.update(scheduler);
    }

    public void delete(Long id, SchedulerDeleteRequestDto requestDto) {
        Scheduler scheduler = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        User user = userLv4Repository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("이메일 없다."));
        if(!user.isMatchPassword(password))
            throw new RuntimeException();
        repository.delete(scheduler);
    }

}
