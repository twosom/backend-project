package com.daib.backend.controller;

import com.daib.backend.form.PostForm;
import com.daib.backend.service.PostService;
import com.daib.backend.validator.post.PostFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @InitBinder("postForm")
    public void postFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PostFormValidator());
    }

    @GetMapping("/post/new")
    public String createNewPostForm(Model model) {
        model.addAttribute(new PostForm());
        return "post/new-post";
    }


    @PostMapping("/post/new")
    public String createNewPost(@Valid PostForm postForm,
                                Errors errors,
                                RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            return "post/new-post";
        }

        postService.createNewPost(postForm);
        redirectAttributes.addFlashAttribute("message", "게시글 작성이 완료되었습니다.");
        return "redirect:/";
    }


}
