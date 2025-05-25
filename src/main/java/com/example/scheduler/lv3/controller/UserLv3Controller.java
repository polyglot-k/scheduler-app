package com.example.scheduler.lv3.controller;

import com.example.scheduler.lv3.dto.UserRequestDto;
import com.example.scheduler.lv3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v3/users")
@RestController
@RequiredArgsConstructor
public class UserLv3Controller {
    private final UserService userService;
    @PostMapping
    ResponseEntity<Void> create(@RequestBody() UserRequestDto request){
        userService.create(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
