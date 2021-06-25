package com.daib.backend.service;

import com.daib.backend.domain.board.Comment;
import com.daib.backend.domain.board.Post;
import com.daib.backend.form.CommentEditForm;
import com.daib.backend.form.CommentForm;
import com.daib.backend.repository.CommentRepository;
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
        Comment comment = commentRepository.findById(id).get();
        modelMapper.map(commentEditForm, comment);
        return comment.getPost().getId();
    }
}
