package com.example.scheduler.lv1.controller;

import com.example.scheduler.lv1.service.SchedulerService;
import com.example.scheduler.lv1.dto.SchedulerResponseDto;
import com.example.scheduler.lv1.dto.SchedulerSearchConditionDto;
import com.example.scheduler.lv1.dto.SchedulerRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/schedulers")
@RestController
@RequiredArgsConstructor
@Slf4j
public class SchedulerController {
    private final SchedulerService service;
    @PostMapping("")
    public ResponseEntity<Void> create(@RequestBody() SchedulerRequestDto request){
        service.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
    @GetMapping("")
    public ResponseEntity<List<SchedulerResponseDto>> findByCondition(SchedulerSearchConditionDto searchConditionDto){
        List<SchedulerResponseDto> schedulers = service.findByCondition(searchConditionDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(schedulers);
    }

    @GetMapping("/{schedulerId}")
    public ResponseEntity<SchedulerResponseDto> findById(@PathVariable Long schedulerId){
        SchedulerResponseDto scheduler = service.findById(schedulerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduler);
    }

}
