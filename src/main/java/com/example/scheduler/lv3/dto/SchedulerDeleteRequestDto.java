package com.example.scheduler.lv3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchedulerDeleteRequestDto {
    String email;
    String password;
}
