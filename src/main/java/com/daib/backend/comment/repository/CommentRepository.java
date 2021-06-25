package com.daib.backend.comment.repository;

import com.daib.backend.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
