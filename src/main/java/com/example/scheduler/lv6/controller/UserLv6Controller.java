package com.example.scheduler.lv6.controller;

import com.example.scheduler.lv6.dto.UserDeleteRequestDto;
import com.example.scheduler.lv6.dto.UserRequestDto;
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
    ResponseEntity<Void> create(@RequestBody @Valid UserRequestDto request){
        userLv6Service.create(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PutMapping()
    ResponseEntity<Void> update(@RequestBody @Valid UserRequestDto request){
        userLv6Service.update(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
    @DeleteMapping()
    ResponseEntity<Void> delete(@RequestBody @Valid UserDeleteRequestDto request){
        userLv6Service.delete(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
