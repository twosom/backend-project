package com.daib.backend.init;

import com.daib.backend.comment.form.CommentForm;
import com.daib.backend.comment.service.CommentService;
import com.daib.backend.post.form.PostForm;
import com.daib.backend.post.repository.PostRepository;
import com.daib.backend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final PostRepository postRepository;
    private final PostService postService;

    private final CommentService commentService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (postRepository.findAll().size() == 0) {
            for (int i = 1; i <= 100; i++) {
                PostForm postForm = PostForm.builder()
                        .title("test-title" + i)
                        .writer("anonymousUser" + i)
                        .content("test-content" + i)
                        .password("11111111")
                        .build();

                Long postId = postService.createNewPost(postForm);

                for (int j = 1; j <= 50; j++) {

                    CommentForm commentForm = CommentForm.builder()
                            .postId(postId)
                            .writer("another anonymousUser" + j)
                            .content("test-content" + j)
                            .build();

                    commentService.createNewComment(commentForm);
                }

            }
        }
    }
}
