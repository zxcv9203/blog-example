package org.example.core.domain.member;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    @Comment("사용자 이름")
    private String name;

    @Column(name = "email", length = 100, nullable = false)
    @Comment("사용자 이메일")
    private String email;

    @Column(name = "password", nullable = false)
    @Comment("사용자 패스워드")
    private String password;

    @Column(name = "created_at", nullable = false)
    @Comment("생성 시간")
    private LocalDateTime createdAt;
}
