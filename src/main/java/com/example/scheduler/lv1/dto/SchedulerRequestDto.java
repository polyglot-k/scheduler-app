package com.example.scheduler.lv1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchedulerRequestDto {
    String todo;
    String writer;
    String password;
}
