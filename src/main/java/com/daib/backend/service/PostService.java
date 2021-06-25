package com.daib.backend.service;

import com.daib.backend.domain.board.Post;
import com.daib.backend.dto.PostViewDto;
import com.daib.backend.form.PostEditForm;
import com.daib.backend.form.PostForm;
import com.daib.backend.repository.PostQueryRepository;
import com.daib.backend.repository.PostRepository;
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

    public void createNewPost(PostForm postForm) {
        Post post = mapper.map(postForm, Post.class);
        postRepository.save(post);
    }

    public void editPost(PostEditForm postEditForm, Long postId) {
        Post findPost = postRepository.findById(postId).get();
        mapper.map(postEditForm, findPost);
    }

    public PostViewDto getPost(Long id) {
        return postQueryRepository.findPostWithCommentById(id);
    }
}
