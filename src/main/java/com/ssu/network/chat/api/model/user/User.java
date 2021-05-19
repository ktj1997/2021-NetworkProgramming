package com.ssu.network.chat.api.model.user;

import com.ssu.network.chat.api.model.common.TimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(indexes = @Index(name = "UserIdIndex", columnList = "userName"))
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, length = 20, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * 1997-12-26
     */
    @Column(nullable = false, length = 10)
    private String birth;

    private String refreshToken;
}
