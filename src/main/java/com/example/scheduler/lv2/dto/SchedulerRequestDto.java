package com.example.scheduler.lv2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchedulerRequestDto {
    String todo;
    String writer;
    String password;
}
