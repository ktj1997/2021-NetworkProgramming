package com.ssu.network.chat.api.service.auth;

import com.ssu.network.chat.api.controller.auth.dtos.request.SignInRequestDto;
import com.ssu.network.chat.api.controller.auth.dtos.request.SignUpRequestDto;
import com.ssu.network.chat.api.controller.auth.dtos.response.SignInResponseDto;
import com.ssu.network.chat.api.dao.user.UserRepository;
import com.ssu.network.chat.api.model.user.User;
import com.ssu.network.chat.api.service.auth.exception.InvalidSignInException;
import com.ssu.network.chat.api.service.user.exception.UserNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.InvalidTimeoutException;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;


    @Override
    public boolean signUp(SignUpRequestDto dto) {
        User user = dto.toEntity();
        userRepository.save(user);
        return true;
    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto dto) {
        User user = userRepository.findByUserName(dto.getUserName()).orElseThrow(UserNotExistException::new);

        if (!user.getPassword().equals(dto.getPassword()))
            throw new InvalidSignInException();
        return new SignInResponseDto("", "");
    }

}
