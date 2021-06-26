package com.daib.backend.comment.validator;

import com.daib.backend.comment.form.CommentForm;
import com.daib.backend.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
public class CommentFormValidator implements Validator {

    private final PostRepository postRepository;


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(CommentForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CommentForm form = (CommentForm) o;

        if (!postRepository.existsByIdAndContentIsNotNull(form.getPostId())) {
            errors.rejectValue("postId", "wrong.postId", "잘못된 접근입니다.");
        }

        if (!StringUtils.hasText(form.getWriter())) {
            errors.rejectValue("writer", "empty.writer", "작성자는 필수입니다.");
        }

        if (!StringUtils.hasText(form.getContent())) {
            errors.rejectValue("content", "empty.content", "내용은 필수입니다.");
        }

    }
}
