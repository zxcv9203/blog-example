package org.example.api.stub;

import org.example.common.post.request.PostCreate;
import org.example.core.domain.post.Post;

public class PostStub {

    private PostStub() {

    }

    public static PostCreate getPostCreate() {
        return PostCreate.builder()
                .title("title")
                .content("content")
                .build();
    }

    public static PostCreate getNoTitlePostCreate() {
        return PostCreate.builder()
                .title("")
                .content("content")
                .build();
    }
}
