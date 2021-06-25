package com.daib.backend.controller;

import com.daib.backend.domain.board.Comment;
import com.daib.backend.domain.board.Post;
import com.daib.backend.dto.PostViewDto;
import com.daib.backend.form.comment.CommentEditForm;
import com.daib.backend.form.comment.CommentForm;
import com.daib.backend.repository.PostRepository;
import com.daib.backend.service.CommentService;
import com.daib.backend.validator.comment.CommentEditFormValidator;
import com.daib.backend.validator.comment.CommentFormValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final CommentFormValidator commentFormValidator;
    private final CommentEditFormValidator commentEditFormValidator;

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;



    @InitBinder("commentForm")
    void commentFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(commentFormValidator);
    }

    @InitBinder("commentEditForm")
    void commentEditFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(commentEditFormValidator);
    }

    @PostMapping("/comment/new")
    public String createNewComment(@Valid CommentForm commentForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            Post post = postRepository.findById(commentForm.getPostId()).orElse(new Post());
            model.addAttribute(modelMapper.map(post, PostViewDto.class));
            return "post/view-post";
        }

        commentService.createNewComment(commentForm);
        return "redirect:/post/" + commentForm.getPostId();
    }

    @GetMapping("/comment/edit/{id}")
    public String editCommentForm(@PathVariable("id") Comment comment, Model model) {
        //TODO 해당 comment 없으면 예외처리
        model.addAttribute(modelMapper.map(comment, CommentEditForm.class));
        return "comment/edit-comment";
    }

    @PostMapping("/comment/edit/{id}")
    public String editComment(@PathVariable("id") Long id, @Valid CommentEditForm commentEditForm, Errors errors) {
        if (errors.hasErrors()) {
            return "comment/edit-comment";
        }

        Long postId = commentService.editComment(commentEditForm, id);
        return "redirect:/post/" + postId;
    }


}
