package com.example.scheduler.lv5.controller;

import com.example.scheduler.lv5.dto.UserRequestDto;
import com.example.scheduler.lv5.service.UserLv5Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v5/users")
@RestController
@RequiredArgsConstructor
public class UserLv5Controller {
    private final UserLv5Service userLv5Service;
    @PostMapping
    ResponseEntity<Void> create(@RequestBody() UserRequestDto request){
        userLv5Service.create(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
