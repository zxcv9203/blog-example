package org.example.common.post.request;

import lombok.*;

@Getter
public class PostSearch {

    private final int size;
    private final int page;

    @Builder
    private PostSearch(Integer size, Integer page) {
        this.size = size == null ? 10 : size;
        this.page = page == null ? 1 : page;
    }

    public long getOffset() {
        return (long) (Math.max(1, page) - 1) * Math.min(size, 2000);
    }
}
