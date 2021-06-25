package com.daib.backend.comment.controller;

import com.daib.backend.comment.domain.Comment;
import com.daib.backend.post.domain.Post;
import com.daib.backend.post.dto.PostViewDto;
import com.daib.backend.comment.form.CommentEditForm;
import com.daib.backend.comment.form.CommentForm;
import com.daib.backend.post.repository.PostRepository;
import com.daib.backend.comment.service.CommentService;
import com.daib.backend.comment.validator.CommentEditFormValidator;
import com.daib.backend.comment.validator.CommentFormValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") Long id) {
        //TODO 없는 id 일 때 예외처리
        Long postId = commentService.deleteComment(id);

        return "redirect:/post/" + postId;
    }


}
