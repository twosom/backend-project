package com.daib.backend.post;

import com.daib.backend.comment.repository.CommentRepository;
import com.daib.backend.post.domain.Post;
import com.daib.backend.post.exception.PostNotFoundException;
import com.daib.backend.post.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @BeforeEach
    void beforeEach() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
    }

    @AfterEach
    void afterEach() {
        postRepository.deleteAll();
    }


    @DisplayName("신규 포스트 작성_성공")
    @Test
    void create_new_post_with_correct_value() throws Exception {
        mockMvc.perform(
                post("/post/new")
                        .param("title", "new post")
                        .param("content", "new content")
                        .param("writer", "anonymous")
                        .param("password", "11111111"))
                .andExpect(status().is3xxRedirection());

        List<Post> postList = postRepository.findAll();
        assertEquals(postList.size(), 1);
        Post post = postList.get(0);
        assertEquals(post.getTitle(), "new post");
        assertEquals(post.getContent(), "new content");
        assertEquals(post.getWriter(), "anonymous");
    }


    @DisplayName("신규 포스트 작성_실패")
    @Test
    void create_new_post_with_wrong_value() throws Exception {

        mockMvc.perform(
                post("/post/new")
                        .param("title", "")
                        .param("content", "")
                        .param("writer", "")
                        .param("password", "11111111"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(view().name("post/new-post"));
    }


    @DisplayName("포스트 수정 - 성공")
    @Test
    void edit_post_with_correct_value() throws Exception {

        create_new_post_with_correct_value();
        List<Post> postList = postRepository.findAll();
        assertEquals(postList.size(), 1);

        Post post = postList.get(0);


        mockMvc.perform(
                post("/post/edit")
                        .param("id", post.getId().toString())
                        .param("title", "edited-title")
                        .param("content", "edited-content")
                        .param("writer", "anonymous")
                        .param("password", post.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/post/" + post.getId()));
        assertEquals(post.getTitle(), "edited-title");

    }

    @DisplayName("포스트 수정 - 실패")
    @Test
    void edit_post_with_wrong_value() throws Exception {

        create_new_post_with_correct_value();
        List<Post> postList = postRepository.findAll();
        assertEquals(postList.size(), 1);

        Post post = postList.get(0);


        mockMvc.perform(
                post("/post/edit")
                        .param("id", post.getId().toString())
                        .param("title", "edited-title")
                        .param("content", "edited-content")
                        .param("writer", "anonymous")
                        .param("password", "not_this_password"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(view().name("post/edit-post"));
    }


    @DisplayName("게시글 조회 - 성공")
    @Test
    void view_post_success() throws Exception {
        create_new_post_with_correct_value();
        List<Post> postList = postRepository.findAll();
        assertEquals(postList.size(), 1);
        Post post = postList.get(0);

        mockMvc.perform(get("/post/{id}", post.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("postViewDto"))
                .andExpect(view().name("post/view-post"));
    }

    @DisplayName("게시글 조회 - 실패")
    @Test
    void view_post_failed() throws Exception {

        int id = 999;
        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> {
                    mockMvc.perform(get("/post/{id}", id));
                })
                .hasCause(new PostNotFoundException(id + "에 해당하는 게시글이 존재하지 않습니다."));

    }

    @DisplayName("게시글 삭제")
    @Test
    void delete_post() throws Exception {
        create_new_post_with_correct_value();
        List<Post> postList = postRepository.findAll();
        assertEquals(postList.size(), 1);
        Post post = postList.get(0);

        mockMvc.perform(delete("/post/delete/{id}", post.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));


        assertNull(post.getContent());
    }

}