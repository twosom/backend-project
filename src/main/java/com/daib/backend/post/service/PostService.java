package com.daib.backend.post.service;

import com.daib.backend.post.domain.Post;
import com.daib.backend.post.dto.PostViewDto;
import com.daib.backend.post.exception.PostNotFoundException;
import com.daib.backend.post.form.PostEditForm;
import com.daib.backend.post.form.PostForm;
import com.daib.backend.post.repository.PostQueryRepository;
import com.daib.backend.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final ModelMapper mapper;

    public Long createNewPost(PostForm postForm) {
        Post post = mapper.map(postForm, Post.class);
        return postRepository.save(post).getId();
    }

    public void editPost(PostEditForm postEditForm) {
        Post findPost = getPostAndValidate(postEditForm.getId());
        mapper.map(postEditForm, findPost);
    }

    public PostViewDto getPost(Long id) {
        return postQueryRepository.findPostWithCommentById(id);
    }

    public void removePost(Long id) {
        Post post = getPostAndValidate(id);
        post.setContent(null);
    }

    public PostEditForm getPostEditForm(Long id) {
        Post post = getPostAndValidate(id);
        return mapper.map(post, PostEditForm.class);
    }

    public Post getPostAndValidate(Long id) {
        Post post = postRepository.findByIdAndContentIsNotNull(id);

        if (post == null) {
            throw new PostNotFoundException(id + "에 해당하는 게시글이 존재하지 않습니다.");
        }

        return post;
    }
}
