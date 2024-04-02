package org.example.core.infrastructure.persistence;

import org.example.common.post.request.PostSearch;
import org.example.core.domain.post.Post;

import java.util.List;

public interface QueryDslPostRepository {

    List<Post> getList(PostSearch postSearch);
}
