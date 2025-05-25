package com.example.scheduler.lv4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SchedulerSearchConditionDto {
    private Long userId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate updatedAt;
    private int page;
    private int size;
}
