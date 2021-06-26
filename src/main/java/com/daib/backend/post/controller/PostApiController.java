package com.daib.backend.post.controller;

import com.daib.backend.post.dto.PostDto;
import com.daib.backend.post.dto.PostViewDto;
import com.daib.backend.post.exception.PostNotFoundException;
import com.daib.backend.post.form.PostEditForm;
import com.daib.backend.post.form.PostForm;
import com.daib.backend.post.repository.PostQueryRepository;
import com.daib.backend.post.service.PostService;
import com.daib.backend.post.validator.PostEditFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostApiController {

    private final PostQueryRepository postQueryRepository;
    private final PostService postService;
    private final PostEditFormValidator postEditFormValidator;

    @InitBinder("postEditForm")
    public void postEditFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(postEditFormValidator);
    }


    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity findAllPosts(Pageable pageable) {
        // DB 상에 존재하는 "모든" Post 데이터를 Comment 와 함께 가져옴.
        Page<PostDto> postList = postQueryRepository.findAll(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postList);
    }

    @GetMapping("/{id}")
    public ResponseEntity viewPost(@PathVariable("id") Long id) {
        try {
            PostViewDto postViewDto = postService.getPost(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(postViewDto);

        } catch (PostNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/new")
    public ResponseEntity createNewPost(@RequestBody @Valid PostForm postForm, Errors errors) {
        if (errors.hasErrors()) {

            List<String> errorMessages = getErrorMessages(errors);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMessages);
        }

        Long postId = postService.createNewPost(postForm);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postId + "번 ID 의 게시글이 생성되었습니다.");
    }

    private List<String> getErrorMessages(Errors errors) {
        return errors.getFieldErrors().stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .collect(Collectors.toList());
    }


    @PostMapping("/edit")
    public ResponseEntity editPost(@Valid @RequestBody PostEditForm postEditForm,
                                   Errors errors) {

        if (errors.hasErrors()) {
            List<String> errorMessages = getErrorMessages(errors);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMessages);
        }

        try {
            postService.editPost(postEditForm);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(postEditForm.getId() + "에 해당하는 게시글의 수정이 완료되었습니다.");
        } catch (PostNotFoundException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePost(@PathVariable("id") Long id) {
        try {
            postService.removePost(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(id + "에 해당하는 게시글의 삭제가 완료되었습니다.");
        } catch (PostNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
