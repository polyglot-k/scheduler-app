package com.example.scheduler.lv4.service;

import com.example.scheduler.lv4.dto.UserDeleteRequestDto;
import com.example.scheduler.lv4.repository.UserLv4Repository;
import com.example.scheduler.lv4.dto.UserRequestDto;
import com.example.scheduler.lv4.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLv4Service {
    private final UserLv4Repository userRepository;
    public void create(UserRequestDto request){
        try {
            User user = User.from(
                    request.getName(),
                    request.getEmail(),
                    request.getPassword()
            );
            userRepository.save(user);
        }catch (DataIntegrityViolationException e) {
            throw new RuntimeException("중복되는 이메일이 있습니다.");
        }
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
