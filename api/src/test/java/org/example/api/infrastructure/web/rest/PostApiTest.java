package org.example.api.infrastructure.web.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostApiTest {

    @Autowired
    private MockMvc mockMvc;


    @Nested
    @DisplayName("테스트")
    class Example {
        @Test
        @DisplayName("/posts 요청시 Hello World 출력")
        void test() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Hello World!"))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("게시글 생성")
    class Create {
        @Test
        @DisplayName("title 값은 필수값입니다.")
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