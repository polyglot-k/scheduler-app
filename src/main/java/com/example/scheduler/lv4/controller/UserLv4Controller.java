package com.example.scheduler.lv4.controller;

import com.example.scheduler.lv4.dto.UserRequestDto;
import com.example.scheduler.lv4.service.UserLv4Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v4/users")
@RestController
@RequiredArgsConstructor
public class UserLv4Controller {
    private final UserLv4Service userLv4Service;
    @PostMapping
    ResponseEntity<Void> create(@RequestBody() UserRequestDto request){
        userLv4Service.create(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
