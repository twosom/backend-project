package com.daib.backend.comment.controller;

import com.daib.backend.comment.dto.CommentDto;
import com.daib.backend.comment.exception.CommentNotFoundException;
import com.daib.backend.comment.form.CommentEditForm;
import com.daib.backend.comment.form.CommentForm;
import com.daib.backend.comment.repository.CommentQueryRepository;
import com.daib.backend.comment.service.CommentService;
import com.daib.backend.comment.validator.CommentEditFormValidator;
import com.daib.backend.comment.validator.CommentFormValidator;
import com.daib.backend.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentQueryRepository commentQueryRepository;
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

    @GetMapping("/all")
    public ResponseEntity findAllComments(Pageable pageable) {
        // DB 상에 존재하는 "모든" Comment 데이터를 가쟈옴.
        Page<CommentDto> commentList = commentQueryRepository.findAll(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentList);
    }

    @PostMapping("/new")
    public ResponseEntity createNewComment(@Valid @RequestBody CommentForm commentForm,
                                           Errors errors) {
        if (errors.hasErrors()) {
            List<String> errorMessages = getErrorMessages(errors);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMessages);
        }

        Long commentId = commentService.createNewComment(commentForm);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentId + "번 ID 의 댓글이 생성되었습니다.");
    }

    @GetMapping("/{id}")
    public ResponseEntity viewComment(@PathVariable("id") Long id) {
        try {
            CommentDto commentDto = commentService.getCommentDto(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(commentDto);
        } catch (CommentNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/edit")
    public ResponseEntity editComment(@Valid @RequestBody CommentEditForm commentEditForm, Errors errors) {
        if (errors.hasErrors()) {
            List<String> errorMessages = getErrorMessages(errors);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMessages);
        }

        commentService.editComment(commentEditForm);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentEditForm.getId() + "에 해당하는 댓글의 수정이 완료되었습니다.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteComment(@PathVariable("id") Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(id + "에 해당하는 댓글의 삭제가 완료되었습니다.");
        } catch (CommentNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    private List<String> getErrorMessages(Errors errors) {
        return errors.getFieldErrors().stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .collect(Collectors.toList());
    }
}
