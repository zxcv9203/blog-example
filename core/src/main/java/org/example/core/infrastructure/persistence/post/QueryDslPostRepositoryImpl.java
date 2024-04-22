package org.example.core.infrastructure.persistence.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.common.post.request.PostSearch;
import org.example.core.domain.post.Post;

import java.util.List;

import static org.example.core.domain.post.QPost.post;

@RequiredArgsConstructor
public class QueryDslPostRepositoryImpl implements QueryDslPostRepository {

    private final JPAQueryFactory query;


    @Override
    public List<Post> getList(PostSearch postSearch) {
        return query
                .selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }
}
