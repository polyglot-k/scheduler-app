package com.example.scheduler.lv3.service;

import com.example.scheduler.lv3.dto.SchedulerDeleteRequestDto;
import com.example.scheduler.lv3.dto.SchedulerRequestDto;
import com.example.scheduler.lv3.dto.SchedulerResponseDto;
import com.example.scheduler.lv3.dto.SchedulerSearchConditionDto;
import com.example.scheduler.lv3.model.Scheduler;
import com.example.scheduler.lv3.model.User;
import com.example.scheduler.lv3.repository.SchedulerLv3Repository;
import com.example.scheduler.lv3.repository.UserLv3Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerLv3Service {

    private final SchedulerLv3Repository repository;
    private final UserLv3Repository userLv3Repository;

    public List<SchedulerResponseDto> findByCondition(SchedulerSearchConditionDto searchConditionDto) {
        return repository.findByCondition(
                    searchConditionDto.getWriter(),
                    searchConditionDto.getUpdatedFrom(),
                    searchConditionDto.getUpdatedTo()
                )
                .stream()
                .map(SchedulerResponseDto::of)
                .toList();
    }

    public SchedulerResponseDto findById(Long id) {
        Scheduler foundScheduler =  repository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));
        return SchedulerResponseDto.of(foundScheduler);
    }

    public void create(SchedulerRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        User user = userLv3Repository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("이메일 없다."));
        if(!user.isMatchPassword(password))
            throw new RuntimeException();
        Scheduler scheduler = Scheduler.from(requestDto.getTodo());
        repository.save(scheduler);
    }
    public void update(Long id, SchedulerRequestDto requestDto) {
        Scheduler scheduler = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        User user = userLv3Repository.findByEmail(email)
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
        User user = userLv3Repository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("이메일 없다."));
        if(!user.isMatchPassword(password))
            throw new RuntimeException();
        repository.delete(scheduler);
    }

}
