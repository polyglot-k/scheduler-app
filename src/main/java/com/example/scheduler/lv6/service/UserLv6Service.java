package com.example.scheduler.lv6.service;

import com.example.scheduler.lv6.dto.UserRequestDto;
import com.example.scheduler.lv6.exception.BusinessException;
import com.example.scheduler.lv6.exception.ErrorCode;
import com.example.scheduler.lv6.model.User;
import com.example.scheduler.lv6.dto.UserDeleteRequestDto;
import com.example.scheduler.lv6.repository.UserLv6Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserLv6Service {
    private final UserLv6Repository userRepository;
    public void create(UserRequestDto request){
        try {
            User user = User.from(
                    request.getName(),
                    request.getEmail(),
                    request.getPassword()
            );
            userRepository.save(user);
        }catch (DataIntegrityViolationException e) {
            log.info(e.getMessage());
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
