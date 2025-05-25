package com.example.scheduler.lv6.controller;

import com.example.scheduler.lv6.dto.*;
import com.example.scheduler.lv6.dto.common.Pageable;
import com.example.scheduler.lv6.service.SchedulerLv6Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/api/v6/schedulers")
@RestController
@Slf4j
@RequiredArgsConstructor
public class SchedulerLv6Controller {
    private final SchedulerLv6Service service;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid  SchedulerRequestDto request){
        service.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
    @GetMapping("")
    public ResponseEntity<?> findByCondition(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate updatedAt,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        SchedulerSearchConditionDto searchConditionDto = new SchedulerSearchConditionDto(userId, updatedAt, page,size);
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
    public ResponseEntity<?> updateById(
            @PathVariable @Valid Long schedulerId,
            @RequestBody @Valid SchedulerRequestDto requestDto){
        service.update(schedulerId, requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{schedulerId}")
    public ResponseEntity<?> deleteById(
            @PathVariable @Valid Long schedulerId,
            @RequestBody @Valid SchedulerDeleteRequestDto requestDto){
        service.delete(schedulerId, requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
