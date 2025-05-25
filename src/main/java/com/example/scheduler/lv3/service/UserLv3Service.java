package com.example.scheduler.lv3.service;

import com.example.scheduler.lv3.dto.UserDeleteRequestDto;
import com.example.scheduler.lv3.dto.UserRequestDto;
import com.example.scheduler.lv3.model.User;
import com.example.scheduler.lv3.repository.UserLv3Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLv3Service {
    private final UserLv3Repository userRepository;
    public void create(UserRequestDto request){
        User user = User.from(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );
        userRepository.save(user);
    }
    public void update(UserRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("존재하지 않는 이메일 입니다."));
        if(!user.isMatchPassword(request.getPassword())){
            throw new RuntimeException("비밀번호가 잘못되었습니다");
        }
        user.updateName(request.getName());

        userRepository.update(user);
    }
    public void delete(UserDeleteRequestDto request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("존재하지 않는 이메일 입니다."));
        if(!user.isMatchPassword(request.getPassword())){
            throw new RuntimeException("비밀번호가 잘못되었습니다");
        }
        userRepository.delete(user);
    }
}
