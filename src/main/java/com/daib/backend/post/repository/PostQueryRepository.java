package com.daib.backend.post.repository;


import com.daib.backend.comment.dto.CommentViewDto;
import com.daib.backend.post.domain.Post;
import com.daib.backend.post.dto.PostListDto;
import com.daib.backend.post.dto.PostViewDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.daib.backend.domain.board.QComment.*;
import static com.daib.backend.domain.board.QPost.*;

@Repository
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    public PostQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    public PostViewDto findPostWithCommentById(Long id) {
        //TODO 조회되지 않으면 예외처리 하기
        PostViewDto postViewDto = queryFactory.select(Projections.fields(PostViewDto.class,
                post.id,
                post.title,
                post.content,
                post.writer,
                post.createdAt))
                .from(post)
                .where(post.id.eq(id))
                .fetchOne();

        List<CommentViewDto> commentList = queryFactory.select(Projections.fields(CommentViewDto.class,
                comment.id,
                comment.writer,
                comment.content,
                comment.createdAt))
                .from(comment)
                .where(comment.post.id.eq(id))
                .fetch();

        postViewDto.setCommentList(commentList);

        return postViewDto;
    }

    public Page<PostListDto> findAllForIndex(Pageable pageable) {
        List<PostListDto> content = queryFactory
                .select(Projections.fields(PostListDto.class,
                        post.id,
                        post.title,
                        post.writer,
                        post.createdAt))
                .from(post)
                .where(post.content.isNotNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Post> countQuery = queryFactory.select(post)
                .from(post)
                .where(post.content.isNotNull());


        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }
}
