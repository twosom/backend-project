package com.daib.backend.comment.repository;


import com.daib.backend.comment.domain.Comment;
import com.daib.backend.comment.dto.CommentDto;
import com.daib.backend.comment.exception.CommentNotFoundException;
import com.daib.backend.domain.board.QComment;
import com.daib.backend.post.domain.Post;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.daib.backend.domain.board.QComment.*;
import static com.daib.backend.domain.board.QPost.post;

@Repository
public class CommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CommentQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    public Page<CommentDto> findAll(Pageable pageable) {

        List<CommentDto> content = queryFactory
                .select(Projections.fields(CommentDto.class,
                        comment.id,
                        comment.post.id.as("postId"),
                        comment.writer,
                        comment.content,
                        comment.createdAt))
                .from(comment)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Comment> countQuery = queryFactory.select(comment)
                .from(comment);


        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    public CommentDto getCommentDto(Long id) {
        List<CommentDto> result = queryFactory
                .select(Projections.fields(CommentDto.class,
                        comment.id,
                        comment.post.id.as("postId"),
                        comment.writer,
                        comment.content,
                        comment.createdAt))
                .from(comment)
                .where(comment.id.eq(id)
                        .and(comment.content.isNotNull()))
                .fetch();

        if (result.isEmpty()) {
            throw new CommentNotFoundException(id + "에 해당하는 댓글이 존재하지 않습니다.");
        }
        return result.get(0);
    }
}
