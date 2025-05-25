package com.example.scheduler.lv6.controller;

import com.example.scheduler.lv5.dto.UserRequestDto;
import com.example.scheduler.lv5.service.UserLv5Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v6/users")
@RestController
@RequiredArgsConstructor
public class UserLv6Controller {
    private final UserLv5Service userLv5Service;

    @PostMapping()
    ResponseEntity<Void> create(@RequestBody @Valid UserRequestDto request){
        userLv5Service.create(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping()
    ResponseEntity<Void> update(@RequestBody @Valid UserRequestDto request){
        userLv5Service.update(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
