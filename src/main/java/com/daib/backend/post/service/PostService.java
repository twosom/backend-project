package com.daib.backend.post.service;

import com.daib.backend.post.domain.Post;
import com.daib.backend.post.dto.PostViewDto;
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

    public void editPost(PostEditForm postEditForm, Long postId) {
        Post findPost = postRepository.findById(postId).get();
        mapper.map(postEditForm, findPost);
    }

    public PostViewDto getPost(Long id) {
        return postQueryRepository.findPostWithCommentById(id);
    }

    public void removePost(Long id) {
        Post post = postRepository.findById(id).get();
        post.setContent(null);
    }
}
