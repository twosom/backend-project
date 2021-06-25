package com.daib.backend.validator.post;

import com.daib.backend.form.post.PostForm;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class PostFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(PostForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PostForm form = (PostForm) o;


        if (!StringUtils.hasText(form.getTitle())) {
            errors.rejectValue("title", "empty.title", "제목은 필수입니다.");
        }

        if (!StringUtils.hasText(form.getWriter())) {
            errors.rejectValue("writer", "empty.writer", "작성자는 필수입니다.");
        }

        if (!StringUtils.hasText(form.getPassword())) {
            errors.rejectValue("password", "empty.password", "비밀번호는 필수입니다.");
        }

        if (!StringUtils.hasText(form.getContent())) {
            errors.rejectValue("content", "empty.content", "내용은 필수입니다.");
        }

    }
}
