package com.example.scheduler.lv2.controller;

import com.example.scheduler.lv2.dto.SchedulerDeleteRequestDto;
import com.example.scheduler.lv2.dto.SchedulerRequestDto;
import com.example.scheduler.lv2.dto.SchedulerResponseDto;
import com.example.scheduler.lv2.dto.SchedulerSearchConditionDto;
import com.example.scheduler.lv2.service.SchedulerLv2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v2/schedulers")
@RestController
@RequiredArgsConstructor
public class SchedulerLv2Controller {
    private final SchedulerLv2Service service;

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
