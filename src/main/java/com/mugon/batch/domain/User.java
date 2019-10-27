package com.mugon.batch.domain;

import com.mugon.batch.domain.enums.Grade;
import com.mugon.batch.domain.enums.SocialType;
import com.mugon.batch.domain.enums.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = {"idx", "email"})
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String principal;

    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;


    //User가 휴면회원으로 판정된 경우 status 필드값을 휴면으로 전환하는 메서드를 추가
    public User setInactive(){
        status = UserStatus.INACTIVE;
        return this;
    }

}
