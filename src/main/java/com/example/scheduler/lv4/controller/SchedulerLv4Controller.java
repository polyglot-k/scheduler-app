package com.example.scheduler.lv4.controller;

import com.example.scheduler.lv4.dto.*;
import com.example.scheduler.lv4.model.Scheduler;
import com.example.scheduler.lv4.service.SchedulerLv4Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v4/schedulers")
@RestController
@Slf4j
@RequiredArgsConstructor
public class SchedulerLv4Controller {
    private final SchedulerLv4Service service;

    @PostMapping("")
    public ResponseEntity<Void> create(@RequestBody() SchedulerRequestDto request){
        service.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
    @GetMapping("")
    public ResponseEntity<List<SchedulerResponseDto>> findByCondition(SchedulerSearchConditionDto searchConditionDto){
        log.info(searchConditionDto.toString());
        Pageable<Scheduler> schedulers = service.findByCondition(searchConditionDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }
    @GetMapping("/{schedulerId}")
    public ResponseEntity<SchedulerResponseDto> findByById(@PathVariable Long schedulerId, @RequestBody SchedulerRequestDto requestDto){
        SchedulerResponseDto scheduler= service.findById(schedulerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduler);
    }

    @PutMapping("/{schedulerId}")
    public ResponseEntity<Void> updateById(@PathVariable Long schedulerId, @RequestBody SchedulerRequestDto requestDto){
        service.update(schedulerId, requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{schedulerId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long schedulerId, @RequestBody SchedulerDeleteRequestDto requestDto){
        service.delete(schedulerId, requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
