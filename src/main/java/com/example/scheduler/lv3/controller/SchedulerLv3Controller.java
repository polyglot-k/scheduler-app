package com.example.scheduler.lv3.controller;

import com.example.scheduler.lv3.dto.SchedulerDeleteRequestDto;
import com.example.scheduler.lv3.dto.SchedulerRequestDto;
import com.example.scheduler.lv3.dto.SchedulerResponseDto;
import com.example.scheduler.lv3.dto.SchedulerSearchConditionDto;
import com.example.scheduler.lv3.service.SchedulerLv3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v3/schedulers")
@RestController
@Slf4j
@RequiredArgsConstructor
public class SchedulerLv3Controller {
    private final SchedulerLv3Service service;

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
        List<SchedulerResponseDto> schedulers = service.findByCondition(searchConditionDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(schedulers);
    }
    @GetMapping("/{schedulerId}")
    public ResponseEntity<SchedulerResponseDto> findByById(@PathVariable Long schedulerId){
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
