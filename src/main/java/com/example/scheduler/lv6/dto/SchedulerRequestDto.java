package com.example.scheduler.lv6.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchedulerRequestDto {
    @NotBlank(message = "할일 내용은 필수입니다.")
    @Size(max = 200, message = "할일은 200자 이내여야 합니다.")
    String todo;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    String password;
}
