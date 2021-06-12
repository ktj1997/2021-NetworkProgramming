package com.ssu.network.chat.api.model.user;

import com.ssu.network.chat.api.model.common.TimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @Setter
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 10)
    private String nickName;

    @Column(nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * YYYY-MM-dd
     */
    @Column(nullable = false)
    private int age;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    UserRole userRole;

    @Setter
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    UserStatus status;

    @Setter
    private String refreshToken;

    @OneToMany(mappedBy = "user")
    public List<UserInterest> interests;

}
