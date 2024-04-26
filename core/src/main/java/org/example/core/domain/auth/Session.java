package org.example.core.domain.auth;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import lombok.*;
import org.example.core.domain.member.Member;

import java.util.UUID;

@Entity
@Table(name = "session")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;

    @ManyToOne
    private Member member;

    @Builder
    public Session(Member member) {
        this.accessToken = UUID.randomUUID().toString();
        this.member = member;
    }
}
