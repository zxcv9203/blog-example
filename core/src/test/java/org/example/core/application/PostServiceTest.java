package org.example.core.application;

import org.example.common.post.request.PostCreate;
import org.example.common.post.stub.PostRequestStub;
import org.example.core.common.JpaConfig;
import org.example.core.domain.post.Post;
import org.example.core.domain.post.PostRepository;
import org.example.core.domain.post.PostRepositoryImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {PostService.class, PostRepositoryImpl.class})
@EnableAutoConfiguration
@Import(JpaConfig.class)
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Nested
    @DisplayName("게시글 작성")
    class Write {

        @Test
        @DisplayName("[성공] 게시글 작성 성공")
        void writeTest() {
            PostCreate postCreate = PostRequestStub.getPostCreate();

            postService.write(postCreate);

            assertThat(postRepository.count()).isEqualTo(1L);
        }
    }

    @Nested
    @DisplayName("게시글 조회")
    class Get {

        @Test
        @DisplayName("[성공] 게시글 조회 성공")
        void getTest() {
            Post want = Post.builder()
                    .title("title")
                    .content("content")
                    .build();
            postRepository.save(want);

            Post got = postService.get(want.getId());

            assertThat(got.getId()).isEqualTo(want.getId());
        }
    }
}