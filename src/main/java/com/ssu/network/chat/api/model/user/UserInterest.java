package com.ssu.network.chat.api.model.user;

import lombok.*;

import javax.persistence.*;

@Getter
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
