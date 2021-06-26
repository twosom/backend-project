package com.daib.backend.post.validator;

import com.daib.backend.post.domain.Post;
import com.daib.backend.post.form.PostEditForm;
import com.daib.backend.post.repository.PostRepository;
import com.daib.backend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
public class PostEditFormValidator implements Validator {

    private final PostRepository postRepository;
    private final PostService postService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(PostEditForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PostEditForm form = (PostEditForm) o;

        if (!postRepository.existsById(form.getId())) {
            errors.rejectValue("id", "wrong.request", "잘못된 요청입니다.");
            return;
        }


        Post findPost = postService.getPostAndValidate(form.getId());


        if (!findPost.getPassword().equals(form.getPassword())) {
            errors.rejectValue("password", "wrong.password", "비밀번호가 일치하지 않습니다.");
        }

    }
}
