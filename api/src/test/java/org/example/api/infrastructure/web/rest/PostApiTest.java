package org.example.api.infrastructure.web.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("게시글 생성")
    class Create {
        @Test
        @DisplayName("[성공] 게시글 생성 성공")
        void test1() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"title\":\"123\",\"content\":\"내용\"}")
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        @DisplayName("[실패] title 값은 필수값입니다.")
        void test2() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"title\":\"\",\"content\":\"내용\"}")
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value() + ""))
                    .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                    .andExpect(jsonPath("$.data.title").value("title 값은 필수값입니다."))
                    .andDo(print());
        }
    }


}