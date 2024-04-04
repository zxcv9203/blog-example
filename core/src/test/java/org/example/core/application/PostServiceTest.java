package org.example.core.application;

import org.example.common.post.request.PostCreate;
import org.example.common.post.request.PostEdit;
import org.example.common.post.request.PostSearch;
import org.example.common.post.response.PostResponse;
import org.example.common.post.stub.PostRequestStub;
import org.example.core.common.JpaConfig;
import org.example.core.common.QueryDslConfig;
import org.example.core.domain.post.Post;
import org.example.core.domain.post.PostRepository;
import org.example.core.domain.post.exception.PostNotFoundException;
import org.example.core.infrastructure.persistence.PostRepositoryImpl;
import org.example.core.infrastructure.persistence.QueryDslPostRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@SpringBootTest(classes = {PostService.class, PostRepositoryImpl.class, QueryDslPostRepositoryImpl.class})
@EnableAutoConfiguration
@Import({JpaConfig.class, QueryDslConfig.class})
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

            PostResponse got = postService.get(want.getId());

            assertThat(got.id()).isEqualTo(want.getId());
        }

        @Test
        @DisplayName("[실패] 존재하지 않는 게시글 조회")
        void notFoundTest() {
            Post want = Post.builder()
                    .title("title")
                    .content("content")
                    .build();
            postRepository.save(want);

            assertThatCode(() -> postService.get(0L))
                    .isInstanceOf(PostNotFoundException.class)
                    .hasMessage("게시글을 찾을 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("게시글 페이지 조회")
    class GetList {

        @Test
        @DisplayName("[성공] 게시글 페이지 조회")
        void getListTest() {
            int want = 10;
            PostSearch postSearch = PostSearch.builder()
                    .page(1)
                    .build();

            List<Post> requestPosts = IntStream.range(1, 31)
                    .mapToObj(i -> Post.builder()
                            .title("title" + i)
                            .content("content" + i)
                            .build())
                    .toList();
            postRepository.saveAll(requestPosts);

            List<PostResponse> got = postService.getList(postSearch);
            assertThat(got).hasSize(want);
        }
    }

    @Nested
    @DisplayName("게시글 수정")
    class Edit {

        @Test
        @DisplayName("[성공] 게시글 수정 성공")
        void editTest() {
            Post want = Post.builder()
                    .title("title")
                    .content("content")
                    .build();
            postRepository.save(want);

            PostEdit request = PostEdit.builder()
                    .title("title2")
                    .content("content2")
                    .build();

            postService.edit(want.getId(), request);

            Post got = postRepository.findById(want.getId())
                    .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

            assertThat(got.getTitle()).isEqualTo(request.title());
            assertThat(got.getContent()).isEqualTo(request.content());
        }

        @Test
        @DisplayName("[실패] 존재하지 않는 게시글 수정")
        void notFoundTest() {
            PostEdit request = PostEdit.builder()
                    .title("title2")
                    .content("content2")
                    .build();

            assertThatCode(() -> postService.edit(0L, request))
                    .isInstanceOf(PostNotFoundException.class)
                    .hasMessage("게시글을 찾을 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("게시글 삭제")
    class Delete {

        @Test
        @DisplayName("[성공] 게시글 삭제 성공")
        void deleteTest() {
            Post want = Post.builder()
                    .title("title")
                    .content("content")
                    .build();
            postRepository.save(want);

            postService.delete(want.getId());

            assertThat(postRepository.count()).isZero();
        }

        @Test
        @DisplayName("[실패] 존재하지 않는 게시글 삭제")
        void notFoundTest() {
            assertThatCode(() -> postService.delete(0L))
                    .isInstanceOf(PostNotFoundException.class)
                    .hasMessage("게시글을 찾을 수 없습니다.");
        }
    }
}