package com.ssu.network.chat.api.dao.user;

import com.ssu.network.chat.api.model.user.User;
import com.ssu.network.chat.api.model.user.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
    void deleteAllByUser(User user);

    List<UserInterest> findAllByUser(User user);
}
