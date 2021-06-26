package com.daib.backend.comment.controller;

import com.daib.backend.comment.form.CommentEditForm;
import com.daib.backend.comment.form.CommentForm;
import com.daib.backend.comment.service.CommentService;
import com.daib.backend.comment.validator.CommentEditFormValidator;
import com.daib.backend.comment.validator.CommentFormValidator;
import com.daib.backend.post.domain.Post;
import com.daib.backend.post.dto.PostViewDto;
import com.daib.backend.post.repository.PostRepository;
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
    public String editCommentForm(@PathVariable("id") Long id, Model model) {
        CommentEditForm commentEditForm = commentService.getCommentEditForm(id);
        model.addAttribute(commentEditForm);
        return "comment/edit-comment";
    }

    @PostMapping("/comment/edit")
    public String editComment(@Valid CommentEditForm commentEditForm, Errors errors) {
        if (errors.hasErrors()) {
            return "comment/edit-comment";
        }

        Long postId = commentService.editComment(commentEditForm);
        return "redirect:/post/" + postId;
    }

    @DeleteMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") Long id) {
        Long postId = commentService.deleteComment(id);

        return "redirect:/post/" + postId;
    }


}
