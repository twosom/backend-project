package com.daib.backend.post.controller;

import com.daib.backend.comment.form.CommentForm;
import com.daib.backend.post.dto.PostViewDto;
import com.daib.backend.post.form.PostEditForm;
import com.daib.backend.post.form.PostForm;
import com.daib.backend.post.service.PostService;
import com.daib.backend.post.validator.PostEditFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostEditFormValidator postEditFormValidator;


    @InitBinder("postEditForm")
    public void postEditFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(postEditFormValidator);
    }


    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable("id") Long id, Model model) {
        PostViewDto postViewDto = postService.getPost(id);
        model.addAttribute(postViewDto);
        model.addAttribute(new CommentForm());
        return "post/view-post";
    }


    @GetMapping("/post/new")
    public String createNewPostForm(Model model) {
        model.addAttribute(new PostForm());
        return "post/new-post";
    }


    @PostMapping("/post/new")
    public String createNewPost(@Valid PostForm postForm,
                                Errors errors) {

        if (errors.hasErrors()) {
            return "post/new-post";
        }

        Long postId = postService.createNewPost(postForm);
        return "redirect:/post/" + postId;
    }


    @GetMapping("/post/edit/{id}")
    public String editPostForm(@PathVariable("id") Long id, Model model) {
        PostEditForm postEditForm = postService.getPostEditForm(id);
        model.addAttribute(postEditForm);
        return "post/edit-post";
    }

    @PostMapping("/post/edit")
    public String editPost(@Valid PostEditForm postEditForm,
                           Errors errors) {
        if (errors.hasErrors()) {
            return "post/edit-post";
        }

        postService.editPost(postEditForm);
        return "redirect:/post/" + postEditForm.getId();
    }

    @DeleteMapping("/post/delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        postService.removePost(id);
        return "redirect:/";
    }
}
