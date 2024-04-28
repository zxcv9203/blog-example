package org.example.api.infrastructure.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.example.common.auth.request.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "example.org", uriPort = 443)
@Sql(scripts = "classpath:db/setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
class AuthApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("로그인")
    class Login {
        private static final String path = "/auth/login";

        @Test
        @DisplayName("[성공] 로그인 성공")
        void successTest() throws Exception {
            LoginRequest request = LoginRequest.builder()
                    .email("admin@example.com")
                    .password("1234")
                    .build();

            mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.accessToken").exists());
        }

        @Test
        @DisplayName("[성공] 로그인후 권한이 필요한 페이지 접속 성공")
        void loginAndAccessTest() throws Exception {

            mockMvc.perform(get("/test")
                            .cookie(new Cookie("SESSION", "6392da07-d89c-4c26-936a-53590471bdbe")))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("[실패] 로그인시 검증되지 않은 세션 값인 경우 예외 발생")
        void loginAndAccessFailTest() throws Exception {

            mockMvc.perform(get("/test")
                            .cookie(new Cookie("SESSION", "invalid")))
                    .andExpect(status().isUnauthorized());
        }
    }


}

