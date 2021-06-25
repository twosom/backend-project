package com.daib.backend.validator.post;

import com.daib.backend.domain.board.Post;
import com.daib.backend.form.PostEditForm;
import com.daib.backend.form.PostForm;
import com.daib.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
public class PostEditFormValidator implements Validator {

    private final PostRepository postRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(PostEditForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PostEditForm form = (PostEditForm) o;

        if (!postRepository.existsById(form.getId())) {
            errors.rejectValue("title", "wrong.request", "잘못된 요청입니다.");
        }


        Post findPost = postRepository.findById(form.getId()).get();


        if (!findPost.getPassword().equals(form.getPassword())) {
            errors.rejectValue("password", "wrong.password", "비밀번호가 일치하지 않습니다.");
        }

        if (!StringUtils.hasText(form.getTitle())) {
            errors.rejectValue("title", "empty.title", "제목은 필수입니다.");
        }

        if (!StringUtils.hasText(form.getWriter())) {
            errors.rejectValue("writer", "empty.writer", "작성자는 필수입니다.");
        }

        if (!StringUtils.hasText(form.getContent())) {
            errors.rejectValue("content", "empty.content", "내용은 필수입니다.");
        }

    }
}
