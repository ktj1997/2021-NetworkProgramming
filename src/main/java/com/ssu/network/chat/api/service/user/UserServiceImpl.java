package com.ssu.network.chat.api.service.user;

import com.ssu.network.chat.api.controller.user.dtos.InterestDto;
import com.ssu.network.chat.api.dao.user.UserInterestRepository;
import com.ssu.network.chat.api.dao.user.UserRepository;
import com.ssu.network.chat.api.model.user.User;
import com.ssu.network.chat.api.model.user.UserInterest;
import com.ssu.network.chat.api.service.user.exception.UserNotExistException;
import com.ssu.network.chat.core.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserInterestRepository userInterestRepository;

    @Override
    public boolean chooseInterest(InterestDto dto) {
        User user = userRepository.findById(SecurityUtil.getUserIdFromToken())
                .orElseThrow(UserNotExistException::new);
        List<UserInterest> newUserInterests = dto.getInterests().stream()
                .map(interest -> new UserInterest(null, user, interest)).collect(Collectors.toList());
        userInterestRepository.deleteAllByUser(user);
        userInterestRepository.saveAll(newUserInterests);

        return true;
    }
}
