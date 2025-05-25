package com.example.scheduler.lv3.service;

import com.example.scheduler.lv3.dto.UserRequestDto;
import com.example.scheduler.lv3.model.User;
import com.example.scheduler.lv3.repository.UserLv3Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserLv3Repository userLv3Repository;
    public void create(UserRequestDto request){
        User user = User.from(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );
        userLv3Repository.save(user);
    }
}
