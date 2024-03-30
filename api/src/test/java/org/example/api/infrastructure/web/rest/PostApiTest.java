package org.example.api.infrastructure.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.stub.PostStub;
import org.example.common.post.request.PostCreate;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("게시글 생성")
    class Create {
        @Test
        @DisplayName("[성공] 게시글 생성 성공")
        void test1() throws Exception {
            PostCreate request = PostStub.getPostCreate();

            mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        @DisplayName("[실패] title 값은 필수값입니다.")
        void test2() throws Exception {
            PostCreate request = PostStub.getNoTitlePostCreate();

            mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value() + ""))
                    .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                    .andExpect(jsonPath("$.data.title").value("title 값은 필수값입니다."))
                    .andDo(print());
        }
    }


}