package com.example.scheduler.lv3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SchedulerSearchConditionDto {
    Long userId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate updatedAt;
}
