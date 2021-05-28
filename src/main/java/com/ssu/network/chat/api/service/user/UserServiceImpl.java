package com.ssu.network.chat.api.service.user;

import com.ssu.network.chat.api.controller.user.dtos.InterestDto;
import com.ssu.network.chat.api.controller.user.dtos.UserDto;
import com.ssu.network.chat.api.dao.user.UserInterestRepository;
import com.ssu.network.chat.api.dao.user.UserRepository;
import com.ssu.network.chat.api.model.user.Interest;
import com.ssu.network.chat.api.model.user.User;
import com.ssu.network.chat.api.model.user.UserInterest;
import com.ssu.network.chat.api.model.user.UserStatus;
import com.ssu.network.chat.api.service.user.exception.UserNotExistException;
import com.ssu.network.chat.core.util.SecurityUtil;
import com.ssu.network.chat.socket.dao.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserInterestRepository userInterestRepository;
    private final ChatRepository chatRepository;

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

    @Override
    public List<UserDto> getOnlineUser() {
        User user = userRepository.findById(SecurityUtil.getUserIdFromToken()).orElseThrow(UserNotExistException::new);
        Set<String> onlineUserName = chatRepository.getOnlineUserList();
        List<Interest> interests = user.getInterests().stream().map(UserInterest::getInterest).collect(Collectors.toList());
        List<User> onlineUser = onlineUserName.stream().map(userRepository::findByUserName)
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

        return onlineUser.stream()
                .filter(filterOnlineUser -> filterOnlineUser.getInterests().stream().anyMatch(
                        filterOnlineUserInterest -> interests.contains(filterOnlineUserInterest.getInterest())
                )).filter(similarInterestUser -> similarInterestUser.getStatus() == UserStatus.ONLINE)
                .map(online ->
                        new UserDto(online.getName(), online.getGender(),
                                new InterestDto(online.getInterests()
                                        .stream().map(UserInterest::getInterest).collect(Collectors.toList()))))
                .collect(Collectors.toList());

    }
}
