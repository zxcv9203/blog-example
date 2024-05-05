package org.example.core.application.auth;

import org.example.common.auth.request.LoginRequest;
import org.example.common.auth.request.SignupRequest;
import org.example.core.common.config.JpaConfig;
import org.example.core.common.config.QueryDslConfig;
import org.example.core.common.security.PasswordEncoder;
import org.example.core.domain.member.Member;
import org.example.core.domain.member.MemberRepository;
import org.example.core.domain.member.exception.DuplicateEmailException;
import org.example.core.domain.member.exception.InvalidSigninException;
import org.example.core.infrastructure.persistence.member.MemberRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest(classes = {AuthService.class, MemberRepositoryImpl.class, PasswordEncoder.class})
@EnableAutoConfiguration
@Import({JpaConfig.class, QueryDslConfig.class})
class AuthServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void teardown() {
        memberRepository.deleteAll();
    }

    @Nested
    @DisplayName("회원가입")
    class Signup {

        @Test
        @DisplayName("[성공] 회원가입에 성공한다.")
        void successTest() {
            long want = 1L;
            SignupRequest signupRequest = SignupRequest.builder()
                    .name("name")
                    .email("email@example.com")
                    .password("password")
                    .build();

            authService.signup(signupRequest);

            long got = memberRepository.count();
            assertThat(got).isEqualTo(want);
        }

        @Test
        @DisplayName("[실패] 중복 이메일인 경우 예외가 발생한다.")
        void duplicateEmailTest() {
            Member member = Member.builder()
                    .name("name")
                    .email("duplicate@example.com")
                    .password("password")
                    .createdAt(LocalDateTime.now())
                    .build();
            memberRepository.save(member);

            SignupRequest signupRequest = SignupRequest.builder()
                    .name("name")
                    .email("duplicate@example.com")
                    .password("password")
                    .build();

            assertThatCode(() -> authService.signup(signupRequest))
                    .isInstanceOf(DuplicateEmailException.class);
        }
    }

    @Nested
    @DisplayName("로그인")
    class Login {

        private final String encodedPassword = passwordEncoder.encrypt("1234");

        @Test
        @DisplayName("[성공] 로그인에 성공한다.")
        void successTest() {
            Member member = Member.builder()
                    .name("name")
                    .email("email@example.com")
                    .password(encodedPassword)
                    .createdAt(LocalDateTime.now())
                    .build();
            memberRepository.save(member);

            LoginRequest request = LoginRequest.builder()
                    .email("email@example.com")
                    .password("1234")
                    .build();

            Long userId = authService.login(request);

            assertThat(userId).isNotNull();
        }


        @Test
        @DisplayName("[실패] 비밀번호가 틀린 경우 예외가 발생한다.")
        void invalidPasswordTest() {
            authService.signup(SignupRequest.builder()
                    .name("name")
                    .email("email@example.com")
                    .password(encodedPassword)
                    .build());
            LoginRequest request = LoginRequest.builder()
                    .email("email@example.com")
                    .password("5678")
                    .build();

            assertThatCode(() -> authService.login(request))
                    .isInstanceOf(InvalidSigninException.class);

        }
    }

}