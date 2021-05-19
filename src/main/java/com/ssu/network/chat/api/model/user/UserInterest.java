package com.ssu.network.chat.api.model.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInterest {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    User user;

    @Enumerated(EnumType.STRING)
    Interest interest;

}
