package com.daib.backend.validator.comment;

import com.daib.backend.form.comment.CommentEditForm;
import com.daib.backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CommentEditFormValidator implements Validator {

    private final CommentRepository commentRepository;



    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(CommentEditForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CommentEditForm form = (CommentEditForm) o;

        if (!commentRepository.existsById(form.getId())) {
            errors.rejectValue("writer", "wrong.request", "잘못된 요청입니다.");
        }
    }
}
