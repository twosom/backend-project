package com.daib.backend.comment.service;

import com.daib.backend.comment.domain.Comment;
import com.daib.backend.comment.dto.CommentDto;
import com.daib.backend.comment.exception.CommentNotFoundException;
import com.daib.backend.comment.form.CommentEditForm;
import com.daib.backend.comment.form.CommentForm;
import com.daib.backend.comment.repository.CommentQueryRepository;
import com.daib.backend.comment.repository.CommentRepository;
import com.daib.backend.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;
    private final ModelMapper modelMapper;

    public Long createNewComment(CommentForm commentForm) {
        Comment comment = modelMapper.map(commentForm, Comment.class);
        Post post = new Post();
        post.setId(commentForm.getPostId());
        comment.setPost(post);
        return commentRepository.save(comment).getId();
    }

    public Long editComment(CommentEditForm commentEditForm) {
        Comment comment = getCommentAndValidate(commentEditForm.getId());
        modelMapper.map(commentEditForm, comment);
        return comment.getPost().getId();
    }

    public Long deleteComment(Long id) {
        Comment comment = getCommentAndValidate(id);
        comment.setContent(null);
        return comment.getPost().getId();
    }

    private Comment getCommentAndValidate(Long id) {
        Comment comment = commentRepository.findByIdAndContentIsNotNull(id);
        if (comment == null) {
            throw new CommentNotFoundException(id + "에 해당하는 댓글이 존재하지 않습니다.");
        }
        return comment;
    }

    public CommentEditForm getCommentEditForm(Long id) {
        Comment comment = getCommentAndValidate(id);
        return modelMapper.map(comment, CommentEditForm.class);
    }

    public CommentDto getCommentDto(Long id) {
        return commentQueryRepository.getCommentDto(id);
    }
}
