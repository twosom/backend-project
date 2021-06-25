package com.daib.backend.controller;

import com.daib.backend.domain.board.Post;
import com.daib.backend.form.PostEditForm;
import com.daib.backend.form.PostForm;
import com.daib.backend.service.PostService;
import com.daib.backend.validator.post.PostEditFormValidator;
import com.daib.backend.validator.post.PostFormValidator;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;
    private final PostEditFormValidator postEditFormValidator;


    @InitBinder("postForm")
    public void postFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PostFormValidator());
    }

    @InitBinder("postEditForm")
    public void postEditFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(postEditFormValidator);
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


    @GetMapping("/post/edit/{id}")
    public String editPostForm(@PathVariable("id") Post post, Model model) {
        //TODO 없는 id 입력 시 예외 처리
        model.addAttribute(modelMapper.map(post, PostEditForm.class));
        return "post/edit-post";
    }

    @PostMapping("/post/edit/{id}")
    public String editPost(@PathVariable("id") Long id,
                           @Valid PostEditForm postEditForm,
                           Errors errors,
                           RedirectAttributes redirectAttributes) {
        //TODO 없는 id 입력 시 예외 처리
        if (errors.hasErrors()) {
            return "post/edit-post";
        }

        postService.editPost(postEditForm, id);
        redirectAttributes.addFlashAttribute("message", "게시글 수정이 완료되었습니다.");
        return "redirect:/";
    }
}
