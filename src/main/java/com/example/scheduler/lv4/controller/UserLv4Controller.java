package com.example.scheduler.lv4.controller;

import com.example.scheduler.lv4.dto.UserRequestDto;
import com.example.scheduler.lv4.service.UserLv4Service;
import com.example.scheduler.lv4.dto.UserDeleteRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping()
    ResponseEntity<Void> update(@RequestBody @Valid UserRequestDto request){
        userLv4Service.update(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping()
    ResponseEntity<Void> delete(@RequestBody @Valid UserDeleteRequestDto request){
        userLv4Service.delete(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
