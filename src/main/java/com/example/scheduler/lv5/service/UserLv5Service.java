package com.example.scheduler.lv5.service;

import com.example.scheduler.lv5.dto.UserDeleteRequestDto;
import com.example.scheduler.lv5.dto.UserRequestDto;
import com.example.scheduler.lv5.exception.BusinessException;
import com.example.scheduler.lv5.exception.ErrorCode;
import com.example.scheduler.lv5.model.User;
import com.example.scheduler.lv5.repository.UserLv5Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLv5Service {
    private final UserLv5Repository userRepository;
    public void create(UserRequestDto request){
        try {
            User user = User.from(
                    request.getName(),
                    request.getEmail(),
                    request.getPassword()
            );
            userRepository.save(user);
        }catch (DataIntegrityViolationException e) {
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATE);
        }
    }
    public void update(UserRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new BusinessException(ErrorCode.INVALID_EMAIL));
        if(!user.isMatchPassword(request.getPassword())){
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }
        user.updateName(request.getName());

        userRepository.update(user);
    }
    public void delete(UserDeleteRequestDto request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new BusinessException(ErrorCode.INVALID_EMAIL));
        if(!user.isMatchPassword(request.getPassword())){
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }
        userRepository.delete(user);
    }
}
