package com.example.scheduler.lv5.controller;

import com.example.scheduler.lv5.dto.*;
import com.example.scheduler.lv5.service.SchedulerLv5Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v5/schedulers")
@RestController
@Slf4j
@RequiredArgsConstructor
public class SchedulerLv5Controller {
    private final SchedulerLv5Service service;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody() SchedulerRequestDto request){
        service.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
    @GetMapping("")
    public ResponseEntity<?> findByCondition(SchedulerSearchConditionDto searchConditionDto){
        log.info(searchConditionDto.toString());
        Pageable<SchedulerResponseDto> schedulers = service.findByCondition(searchConditionDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(schedulers);
    }
    @GetMapping("/{schedulerId}")
    public ResponseEntity<?> findByById(@PathVariable Long schedulerId, @RequestBody SchedulerRequestDto requestDto){
        SchedulerResponseDto scheduler= service.findById(schedulerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduler);
    }

    @PutMapping("/{schedulerId}")
    public ResponseEntity<?> updateById(@PathVariable Long schedulerId, @RequestBody SchedulerRequestDto requestDto){
        service.update(schedulerId, requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{schedulerId}")
    public ResponseEntity<?> deleteById(@PathVariable Long schedulerId, @RequestBody SchedulerDeleteRequestDto requestDto){
        service.delete(schedulerId, requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
