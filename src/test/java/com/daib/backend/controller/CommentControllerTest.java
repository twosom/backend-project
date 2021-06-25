package com.daib.backend.controller;

import com.daib.backend.domain.board.Comment;
import com.daib.backend.domain.board.Post;
import com.daib.backend.form.PostForm;
import com.daib.backend.repository.CommentRepository;
import com.daib.backend.repository.PostRepository;
import com.daib.backend.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    CommentRepository commentRepository;

    @BeforeEach
    void beforeEach() {
        PostForm postForm = PostForm.builder()
                .title("test title")
                .writer("anonymous")
                .content("test content")
                .password("11111111")
                .build();

        postService.createNewPost(postForm);
    }


    @AfterEach
    void afterEach() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
    }


    @DisplayName("댓글 추가 - 성공")
    @Test
    void create_new_comment_with_correct_value() throws Exception {

        List<Post> postList = postRepository.findAll();
        Post post = postList.get(0);
        assertNotNull(post);


        mockMvc.perform(
                post("/comment/new")
                        .param("postId", post.getId().toString())
                        .param("writer", "another anonymous")
                        .param("content", "test comment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/post/" + post.getId()));

        List<Comment> commentList = commentRepository.findAll();
        assertEquals(commentList.size(), 1);
        Comment comment = commentList.get(0);
        assertEquals(comment.getPost().getId(), post.getId());
        assertEquals(comment.getWriter(), "another anonymous");
        assertEquals(comment.getContent(), "test comment");
    }

}