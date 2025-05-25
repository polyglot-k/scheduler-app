package com.example.scheduler.lv5.service;

import com.example.scheduler.lv5.dto.*;
import com.example.scheduler.lv5.exception.BusinessException;
import com.example.scheduler.lv5.exception.ErrorCode;
import com.example.scheduler.lv5.model.Scheduler;
import com.example.scheduler.lv5.model.User;
import com.example.scheduler.lv5.repository.SchedulerLv5Repository;
import com.example.scheduler.lv5.repository.UserLv5Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerLv5Service {

    private final SchedulerLv5Repository repository;
    private final UserLv5Repository userRepository;

    public Pageable<SchedulerResponseDto> findByCondition(SchedulerSearchConditionDto searchConditionDto) {
        Pageable<Scheduler> pageResult = repository.findByCondition(
                searchConditionDto.getUserId(),
                searchConditionDto.getUpdatedAt(),
                searchConditionDto.getPage() - 1,
                searchConditionDto.getSize()
        );

        List<SchedulerResponseDto> dtoList = pageResult.getContent().stream()
                .map(SchedulerResponseDto::of)
                .toList();

        return new Pageable<>(
                dtoList,
                pageResult.getCurrentPage(),
                pageResult.getPageSize(),
                pageResult.getTotalElements()
        );
    }

    public SchedulerResponseDto findById(Long id) {
        Scheduler foundScheduler = repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCHEDULER_NOT_FOUND));
        return SchedulerResponseDto.of(foundScheduler);
    }

    public void create(SchedulerRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_EMAIL));

        if (!user.isMatchPassword(password))
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);

        Scheduler scheduler = Scheduler.from(requestDto.getTodo(), user);
        repository.save(scheduler);
    }

    public void update(Long id, SchedulerRequestDto requestDto) {
        Scheduler scheduler = repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCHEDULER_NOT_FOUND));

        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_EMAIL));

        if (!user.isMatchPassword(password))
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);

        scheduler.updateTodo(requestDto.getTodo());
        repository.update(scheduler);
    }

    public void delete(Long id, SchedulerDeleteRequestDto requestDto) {
        Scheduler scheduler = repository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCHEDULER_NOT_FOUND));

        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_EMAIL));

        if (!user.isMatchPassword(password))
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);

        repository.delete(scheduler);
    }

}
