package com.example.scheduler.lv3.dto;

import com.example.scheduler.lv3.model.Scheduler;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class SchedulerResponseDto {
    private Long id;
    private String todo;
    private String writer;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public static SchedulerResponseDto of(Scheduler foundScheduler) {
        return new SchedulerResponseDto(
                foundScheduler.getId(),
                foundScheduler.getTodo(),
                foundScheduler.getUser().getName(),
                foundScheduler.getCreatedAt(),
                foundScheduler.getUpdatedAt()
        );
    }
    public String getCreatedAt() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getUpdatedAt() {
        return updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
