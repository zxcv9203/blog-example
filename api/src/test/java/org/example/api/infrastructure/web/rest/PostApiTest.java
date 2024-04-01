package org.example.api.infrastructure.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common.post.request.PostCreate;
import org.example.common.post.stub.PostRequestStub;
import org.example.core.domain.post.Post;
import org.example.core.domain.post.PostRepository;
import org.junit.jupiter.api.AfterEach;
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

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }
    @Nested
    @DisplayName("게시글 생성")
    class Create {
        @Test
        @DisplayName("[성공] 게시글 생성 성공")
        void test1() throws Exception {
            PostCreate request = PostRequestStub.getPostCreate();

            mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isCreated())
                    .andDo(print());
        }

        @Test
        @DisplayName("[실패] title 값은 필수값입니다.")
        void test2() throws Exception {
            PostCreate request = PostRequestStub.getNoTitlePostCreate();

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

    @Nested
    @DisplayName("게시글 단건 조회")
    class Get {

        private final String path = "/posts/{id}";

        @Test
        @DisplayName("[성공] 게시글 단건 조회 성공")
        void getTest() throws Exception {
            Post post = Post.builder()
                    .title("title")
                    .content("content")
                    .build();
            postRepository.save(post);

            mockMvc.perform(MockMvcRequestBuilders.get(path, post.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(post.getId() + ""))
                    .andExpect(jsonPath("$.title").value(post.getTitle()))
                    .andExpect(jsonPath("$.content").value(post.getContent()))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("게시글 목록 조회")
    class GetList {
        @Test
        @DisplayName("[성공] 게시글 목록 조회 성공")
        void getListTest() throws Exception {
            int want = 2;
            Post post1 = Post.builder()
                    .title("title")
                    .content("content")
                    .build();
            Post post2 = Post.builder()
                    .title("title")
                    .content("content")
                    .build();
            postRepository.save(post1);
            postRepository.save(post2);

            mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(want))
                    .andDo(print());
        }
    }
}