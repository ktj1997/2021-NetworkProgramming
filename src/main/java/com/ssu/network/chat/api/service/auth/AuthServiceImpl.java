package com.ssu.network.chat.api.service.auth;

import com.ssu.network.chat.api.controller.auth.dtos.request.SignInRequestDto;
import com.ssu.network.chat.api.controller.auth.dtos.request.SignUpRequestDto;
import com.ssu.network.chat.api.controller.auth.dtos.response.SignInResponseDto;
import com.ssu.network.chat.api.dao.user.UserRepository;
import com.ssu.network.chat.api.model.user.User;
import com.ssu.network.chat.api.service.auth.exception.InvalidSignInException;
import com.ssu.network.chat.api.service.user.exception.UserNotExistException;
import com.ssu.network.chat.core.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    @Override
    public boolean signUp(SignUpRequestDto dto) {
        User user = dto.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto dto) {
        User user = userRepository.findByUserName(dto.getUserName()).orElseThrow(UserNotExistException::new);

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw new InvalidSignInException();

        String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getUserRole());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId(), user.getUserRole());
        user.setRefreshToken(refreshToken);
        return new SignInResponseDto(accessToken, refreshToken);
    }

    @Override
    public boolean checkIdDuplication(String id) {
        return !userRepository.existsByUserName(id);
    }

}
