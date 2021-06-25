package com.daib.backend.comment.validator;

import com.daib.backend.comment.form.CommentEditForm;
import com.daib.backend.comment.repository.CommentRepository;
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
