package com.daib.backend.comment.service;

import com.daib.backend.comment.domain.Comment;
import com.daib.backend.comment.exception.CommentNotFoundException;
import com.daib.backend.post.domain.Post;
import com.daib.backend.comment.form.CommentEditForm;
import com.daib.backend.comment.form.CommentForm;
import com.daib.backend.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public void createNewComment(CommentForm commentForm) {
        Comment comment = modelMapper.map(commentForm, Comment.class);
        Post post = new Post();
        post.setId(commentForm.getPostId());
        comment.setPost(post);
        commentRepository.save(comment);
    }

    public Long editComment(CommentEditForm commentEditForm, Long id) {
        Comment comment = getCommentAndValidate(id);
        modelMapper.map(commentEditForm, comment);
        return comment.getPost().getId();
    }

    public Long deleteComment(Long id) {
        Comment comment = getCommentAndValidate(id);
        comment.setContent(null);
        return comment.getPost().getId();
    }

    private Comment getCommentAndValidate(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id + "에 해당하는 댓글이 존재하지 않습니다."));
    }

    public CommentEditForm getCommentEditForm(Long id) {
        Comment comment = getCommentAndValidate(id);
        return modelMapper.map(comment, CommentEditForm.class);
    }
}
