package org.example.common.post.stub;

import org.example.common.post.request.PostCreate;

public class PostRequestStub {

    private PostRequestStub() {

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
