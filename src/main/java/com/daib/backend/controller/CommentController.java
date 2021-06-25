package com.daib.backend.controller;

import com.daib.backend.domain.board.Post;
import com.daib.backend.dto.PostViewDto;
import com.daib.backend.form.CommentForm;
import com.daib.backend.repository.PostRepository;
import com.daib.backend.service.CommentService;
import com.daib.backend.validator.comment.CommentFormValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentFormValidator commentFormValidator;

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;


    @InitBinder("commentForm")
    void commentFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(commentFormValidator);
    }

    @PostMapping("/comment/new")
    public String createNewComment(@Valid CommentForm commentForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            Post post = postRepository.findById(commentForm.getPostId()).get();
            model.addAttribute(modelMapper.map(post, PostViewDto.class));
            return "post/view-post";
        }

        commentService.createNewComment(commentForm);
        return "redirect:/post/" + commentForm.getPostId();
    }
}
