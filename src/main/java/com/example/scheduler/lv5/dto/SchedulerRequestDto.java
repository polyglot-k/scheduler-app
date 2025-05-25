package com.example.scheduler.lv5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchedulerRequestDto {
    String todo;
    String email;
    String password;
}
