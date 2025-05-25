package com.example.scheduler.lv1.dto;

import com.example.scheduler.lv1.model.Scheduler;
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
                foundScheduler.getWriter(),
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
