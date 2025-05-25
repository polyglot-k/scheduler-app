package com.example.scheduler.lv2.service;

import com.example.scheduler.lv2.dto.SchedulerDeleteRequestDto;
import com.example.scheduler.lv2.dto.SchedulerRequestDto;
import com.example.scheduler.lv2.dto.SchedulerResponseDto;
import com.example.scheduler.lv2.dto.SchedulerSearchConditionDto;
import com.example.scheduler.lv2.model.Scheduler;
import com.example.scheduler.lv2.repository.SchedulerLv2Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerLv2Service {

    private final SchedulerLv2Repository repository;

    public List<SchedulerResponseDto> findByCondition(SchedulerSearchConditionDto searchConditionDto) {
        return repository.findByCondition(
                    searchConditionDto.getWriter(),
                    searchConditionDto.getUpdatedAt()
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
        Scheduler scheduler = Scheduler.from(
                requestDto.getTodo(),
                requestDto.getWriter(),
                requestDto.getPassword()
        );
        repository.save(scheduler);
    }
    public void update(Long id, SchedulerRequestDto requestDto) {
        Scheduler scheduler = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));
        verifyPassword(scheduler, requestDto.getPassword());

        scheduler.updateTodoAndWriter(
                requestDto.getTodo(),
                requestDto.getWriter()
        );
        log.info(scheduler.toString());
        repository.update(scheduler);
    }

    public void delete(Long id, SchedulerDeleteRequestDto requestDto) {
        Scheduler scheduler = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("일정이 존재하지 않습니다."));
        verifyPassword(scheduler, requestDto.getPassword());
        repository.delete(scheduler);
    }

    private void verifyPassword(Scheduler scheduler, String inputPassword) {
        if (!scheduler.isMatchPassword(inputPassword)) {
            throw new RuntimeException("패스워드가 일치하지 않습니다.");
        }
    }

}
