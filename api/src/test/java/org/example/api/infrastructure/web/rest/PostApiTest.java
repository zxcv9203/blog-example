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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "example.org", uriPort = 443)
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
                    .andDo(print())
                    .andDo(document("post-create",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestFields(
                                    fieldWithPath("title").description("게시글 제목"),
                                    fieldWithPath("content").description("게시글 내용")
                            )
                    ));
        }

        @Test
        @DisplayName("[실패] title 값은 필수값입니다.")
        void test2() throws Exception {
            PostCreate request = PostRequestStub.getNoTitlePostCreate();

            mockMvc.perform(RestDocumentationRequestBuilders.post("/posts")
                            .header("Authorization", "admin")
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value() + ""))
                    .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                    .andExpect(jsonPath("$.data.title").value("title 값은 필수값입니다."))
                    .andDo(print())
                    .andDo(document("post-create/fail/required-title",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            requestFields(
                                    fieldWithPath("title").description("게시글 제목"),
                                    fieldWithPath("content").description("게시글 내용")
                            ),
                            responseFields(
                                    fieldWithPath("code").description("HTTP 상태 코드"),
                                    fieldWithPath("message").description("에러 메시지"),
                                    fieldWithPath("data.title").description("내용")
                            )
                    ));
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

            mockMvc.perform(RestDocumentationRequestBuilders.get(path, post.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(post.getId() + ""))
                    .andExpect(jsonPath("$.title").value(post.getTitle()))
                    .andExpect(jsonPath("$.content").value(post.getContent()))
                    .andDo(print())
                    .andDo(document("post-get",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            pathParameters(
                                    parameterWithName("id").description("게시글 ID")
                            ),
                            responseFields(
                                    fieldWithPath("id").description("게시글 ID"),
                                    fieldWithPath("title").description("게시글 제목"),
                                    fieldWithPath("content").description("게시글 내용")
                            )
                    ));

        }

        @Test
        @DisplayName("[실패] 존재하지 않는 게시글 조회")
        void notFoundTest() throws Exception {
            mockMvc.perform(RestDocumentationRequestBuilders.get(path, 0))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value() + ""))
                    .andExpect(jsonPath("$.message").value("게시글을 찾을 수 없습니다."))
                    .andDo(print())
                    .andDo(document("post-get/fail/not-found",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            pathParameters(
                                    parameterWithName("id").description("게시글 ID")
                            ),
                            responseFields(
                                    fieldWithPath("code").description("HTTP 상태 코드"),
                                    fieldWithPath("message").description("에러 메시지"),
                                    fieldWithPath("data").description("내용")
                            )
                    ));

        }
    }

    @Nested
    @DisplayName("게시글 목록 조회")
    class GetList {
        @Test
        @DisplayName("[성공] 게시글 목록 조회 성공")
        void getListTest() throws Exception {
            int want = 10;
            List<Post> requestPosts = IntStream.range(1, 31)
                    .mapToObj(i -> Post.builder()
                            .title("title" + i)
                            .content("content" + i)
                            .build())
                    .toList();
            postRepository.saveAll(requestPosts);

            mockMvc.perform(RestDocumentationRequestBuilders.get("/posts?page=1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(want))
                    .andDo(print())
                    .andDo(document("post-get-list",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            queryParameters(
                                    parameterWithName("page").description("페이지 번호")
                            ),
                            responseFields(
                                    fieldWithPath("[].id").description("게시글 ID"),
                                    fieldWithPath("[].title").description("게시글 제목"),
                                    fieldWithPath("[].content").description("게시글 내용")
                            )
                    ));

        }

        @Test
        @DisplayName("[성공] 페이지를 0으로 요청하면 첫 페이지를 가져올 수 있다.")
        void getPageZeroTest() throws Exception {
            int want = 10;
            List<Post> requestPosts = IntStream.range(1, 31)
                    .mapToObj(i -> Post.builder()
                            .title("title" + i)
                            .content("content" + i)
                            .build())
                    .toList();
            postRepository.saveAll(requestPosts);

            mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(want))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("게시글 수정")
    class Edit {
        @Test
        @DisplayName("[성공] 게시글 수정 성공")
        void editTest() throws Exception {
            Post post = Post.builder()
                    .title("title")
                    .content("content")
                    .build();
            postRepository.save(post);

            PostCreate request = PostRequestStub.getPostCreate();

            mockMvc.perform(RestDocumentationRequestBuilders.patch("/posts/{id}", post.getId())
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andDo(document("post-edit",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            pathParameters(
                                    parameterWithName("id").description("게시글 ID")
                            ),
                            requestFields(
                                    fieldWithPath("title").description("게시글 제목"),
                                    fieldWithPath("content").description("게시글 내용")
                            )
                    ));
        }

        @Test
        @DisplayName("[실패] 존재하지 않는 게시글 수정시 실패합니다.")
        void notFoundTest() throws Exception {
            PostCreate request = PostRequestStub.getPostCreate();

            mockMvc.perform(RestDocumentationRequestBuilders.patch("/posts/{id}", 0)
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value() + ""))
                    .andExpect(jsonPath("$.message").value("게시글을 찾을 수 없습니다."))
                    .andDo(print())
                    .andDo(document("post-edit/fail/not-found",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            pathParameters(
                                    parameterWithName("id").description("게시글 ID")
                            ),
                            responseFields(
                                    fieldWithPath("code").description("HTTP 상태 코드"),
                                    fieldWithPath("message").description("에러 메시지"),
                                    fieldWithPath("data").description("내용")
                            )
                    ));
        }
    }

    @Nested
    @DisplayName("게시글 삭제")
    class Delete {
        @Test
        @DisplayName("[성공] 게시글 삭제 성공")
        void deleteTest() throws Exception {
            Post post = Post.builder()
                    .title("title")
                    .content("content")
                    .build();
            postRepository.save(post);

            mockMvc.perform(RestDocumentationRequestBuilders.delete("/posts/{id}", post.getId()))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andDo(document("post-delete",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            pathParameters(
                                    parameterWithName("id").description("게시글 ID")
                            )
                    ));
        }

        @Test
        @DisplayName("[실패] 존재하지 않는 게시글 삭제")
        void notFoundTest() throws Exception {
            mockMvc.perform(RestDocumentationRequestBuilders.delete("/posts/{id}", 0))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value() + ""))
                    .andExpect(jsonPath("$.message").value("게시글을 찾을 수 없습니다."))
                    .andDo(print())
                    .andDo(document("post-delete/fail/not-found",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            pathParameters(
                                    parameterWithName("id").description("게시글 ID")
                            ),
                            responseFields(
                                    fieldWithPath("code").description("HTTP 상태 코드"),
                                    fieldWithPath("message").description("에러 메시지"),
                                    fieldWithPath("data").description("내용")
                            )
                    ));
        }
    }
}