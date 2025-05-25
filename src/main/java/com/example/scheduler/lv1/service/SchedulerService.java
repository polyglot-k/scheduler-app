package com.example.scheduler.lv1.service;

import com.example.scheduler.lv1.dto.SchedulerRequestDto;
import com.example.scheduler.lv1.dto.SchedulerResponseDto;
import com.example.scheduler.lv1.dto.SchedulerSearchConditionDto;
import com.example.scheduler.lv1.model.Scheduler;
import com.example.scheduler.lv1.repository.SchedulerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final SchedulerRepository repository;

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
}
