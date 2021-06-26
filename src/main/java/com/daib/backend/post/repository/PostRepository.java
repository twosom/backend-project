package com.daib.backend.post.repository;

import com.daib.backend.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByIdAndContentIsNotNull(Long id);

    boolean existsByIdAndContentIsNotNull(Long id);
}
