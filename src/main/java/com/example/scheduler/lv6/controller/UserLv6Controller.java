package com.example.scheduler.lv6.controller;

import com.example.scheduler.lv6.dto.UserDeleteRequestDto;
import com.example.scheduler.lv6.dto.UserRequestDto;
import com.example.scheduler.lv6.dto.common.SuccessResponse;
import com.example.scheduler.lv6.service.UserLv6Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v6/users")
@RestController
@RequiredArgsConstructor
public class UserLv6Controller {
    private final UserLv6Service userLv6Service;

    @PostMapping()
    ResponseEntity<?> create(@RequestBody @Valid UserRequestDto request){
        userLv6Service.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.of("사용자가 생성되었습니다.", null));
    }

    @PutMapping()
    ResponseEntity<?> update(@RequestBody @Valid UserRequestDto request){
        userLv6Service.update(request);
        return ResponseEntity
                .ok(SuccessResponse.of("사용자 정보가 수정되었습니다.", null));
    }
    @DeleteMapping()
    ResponseEntity<?> delete(@RequestBody @Valid UserDeleteRequestDto request){
        userLv6Service.delete(request);
        return ResponseEntity
                .ok(SuccessResponse.of("사용자가 삭제되었습니다.", null));
    }
}
